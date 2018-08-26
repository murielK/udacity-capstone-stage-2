package hr.murielkamgang.mysubreddits.components.subreddit;

import android.util.SparseBooleanArray;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseContentListPresenter;
import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.data.source.subreddit.SubredditRepository;
import hr.murielkamgang.mysubreddits.data.source.thread.RedditThreadRepository;
import hr.murielkamgang.mysubreddits.helper.PreferenceHelper;
import hr.murielkamgang.mysubreddits.util.Utils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class SubredditPresenter extends BaseContentListPresenter<Subreddit, SubredditContract.View> implements SubredditContract.Presenter {

    private static final int REPEAT_INTERVAL = 15;
    private static final int MAX_SELECTION = 10;
    private final SubredditRepository subredditRepository;
    private final RedditThreadRepository redditThreadStringRepository;
    private final PreferenceHelper preferenceHelper;
    private final FirebaseAnalytics firebaseAnalytics;
    private Logger logger = LoggerFactory.getLogger(SubredditPresenter.class);
    private Disposable disposable;

    @Inject
    SubredditPresenter(PreferenceHelper preferenceHelper
            , SubredditRepository subredditRepository
            , RedditThreadRepository redditThreadStringRepository
            , FirebaseAnalytics firebaseAnalytics) {
        this.preferenceHelper = preferenceHelper;
        this.subredditRepository = subredditRepository;
        this.redditThreadStringRepository = redditThreadStringRepository;
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    protected Observable<List<Subreddit>> provideLoadObservable(boolean sync) {
        return subredditRepository.getAllDataAsObservable();
    }

    @Override
    public void setView(SubredditContract.View view) {
        if (preferenceHelper.isConfigured()) {
            view.goNext();
            return;
        }
        super.setView(view);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (disposable != null) {
            disposable.dispose();
        }
    }

    @Override
    public void delegateOkClicked(SparseBooleanArray data) {
        if (data.size() == 0) {
            view.toast(R.string.error_msg_emty_subreddit_selection);
            return;
        }

        if (data.size() > 10) {
            view.toast(view.getContext().getString(R.string.error_msg_max_selection, MAX_SELECTION));
            return;
        }

        resetWorker();
        view.showDialog(R.string.processing, false);

        final List<Subreddit> subreddits = new ArrayList<>(data.size());
        for (int i = 0, count = data.size(); i < count; i++) {
            subreddits.add(oldItems.get(data.keyAt(i)));
        }

        disposable =
                subredditRepository.getLocalDataSource()
                        .deleteAllAsObservable()
                        .subscribeOn(Schedulers.io())
                        .andThen(Observable.fromIterable(subreddits))
                        .doOnNext(subreddit -> {
                            logger.debug("saving {} subreddit", subreddit.getDisplayNamePrefixed());
                            Utils.trackSelectedSubreddit(firebaseAnalytics, subreddit);
                            subredditRepository.getLocalDataSource().saveData(subreddit);
                        })
                        .flatMap((Function<Subreddit, ObservableSource<List<RedditThread>>>)
                                subreddit -> redditThreadStringRepository.getRemoteDataSource()
                                        .getAllDataAsObservable(subreddit.getDisplayName()))
                        .doOnNext(redditThreads -> {
                            logger.debug("saving {} redditThreads", redditThreads.size());
                            redditThreadStringRepository.getLocalDataSource().saveData(redditThreads);
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .filter(redditThreads -> !redditThreads.isEmpty())
                        .subscribe(redditThreads -> {
                            //lets set it to configured if we get at least few threads.
                            if (!preferenceHelper.isConfigured()) {
                                configureWorker();
                            }
                        }, throwable -> {
                            if (view != null) {
                                view.dismissDialog();

                                if (preferenceHelper.isConfigured()) {
                                    view.goNext();
                                } else {
                                    view.toast(R.string.error_msg_something_went_wrong);
                                }
                            }

                            logger.debug("", throwable);
                        }, () -> {
                            if (view != null) {
                                view.dismissDialog();

                                if (preferenceHelper.isConfigured()) {
                                    view.goNext();
                                }
                            }
                        });


    }

    private void configureWorker() {
        if (!preferenceHelper.isConfigured()) {
            preferenceHelper.setConfigured();
            final WorkRequest wr = new PeriodicWorkRequest.Builder(LoadRedditThreadWorker.class, REPEAT_INTERVAL, TimeUnit.MINUTES)
                    .setConstraints(new Constraints.Builder().setRequiresBatteryNotLow(true).setRequiredNetworkType(NetworkType.CONNECTED).build()).build();
            WorkManager.getInstance().enqueue(wr);
        }
    }

    private void resetWorker() {
        preferenceHelper.setNotConfigured();
        WorkManager.getInstance().cancelAllWorkByTag(LoadRedditThreadWorker.WORK_TAG);
    }
}
