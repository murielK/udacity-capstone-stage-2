package hr.murielkamgang.mysubreddits.components.reddithread;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.support.v7.util.DiffUtil;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseContentListPresenter;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.data.source.thread.ReddiThreadLocalDataSource;
import hr.murielkamgang.mysubreddits.data.source.thread.RedditThreadRepository;
import hr.murielkamgang.mysubreddits.helper.PreferenceHelper;
import hr.murielkamgang.mysubreddits.util.Utils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RedditThreadPresenter extends BaseContentListPresenter<RedditThread, RedditThreadContract.View> implements RedditThreadContract.Presenter {

    private final Logger logger = LoggerFactory.getLogger(RedditThreadPresenter.class);
    private final RedditThreadRepository redditThreadRepository;
    private final FirebaseAnalytics firebaseAnalytics;
    private LiveData<List<RedditThread>> listLiveData;
    private PreferenceHelper preferenceHelper;

    @Inject
    RedditThreadPresenter(RedditThreadRepository redditThreadRepository
            , PreferenceHelper preferenceHelper
            , FirebaseAnalytics firebaseAnalytics) {
        this.redditThreadRepository = redditThreadRepository;
        this.preferenceHelper = preferenceHelper;
        this.firebaseAnalytics = firebaseAnalytics;
    }

    @Override
    public void setView(RedditThreadContract.View view) {
        super.setView(view);

        listLiveData = ((ReddiThreadLocalDataSource) redditThreadRepository.getLocalDataSource()).getAllDataLive();
        listLiveData.observe((LifecycleOwner) view.getContext(), redditThreads -> {
            logger.debug("something has changed!");
            load();
        });
    }

    @Override
    public void onDestroy() {
        listLiveData.removeObservers((LifecycleOwner) view.getContext());
        super.onDestroy();
    }

    @Override
    protected Observable<List<RedditThread>> provideLoadObservable(boolean sync) {
        return redditThreadRepository.getLocalDataSource()
                .getAllDataAsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public void delegateOnItemClicked(RedditThread redditThread) {
        if (view.getContext().getResources().getBoolean(R.bool.landscape)) {
            view.viewCommentInFragmentFor(redditThread);
        } else {
            view.viewCommentFor(redditThread);
        }
        Utils.trackSelectRedditThread(firebaseAnalytics, redditThread);
    }

    @Override
    public void delegateOnCardClicked(RedditThread redditThread) {
        final String youtubeVideoKey;
        if ((youtubeVideoKey = Utils.getYoutubeKey(redditThread)) != null) {
            view.openYoutube(youtubeVideoKey);
            Utils.trackViewRedditThread(firebaseAnalytics, redditThread, "Youtube");
        } else {
            view.viewImage(redditThread.getPreview().getImages().get(0).getSource().getUrl());
            Utils.trackViewRedditThread(firebaseAnalytics, redditThread, "Image");
        }
    }

    @Override
    public void delegateResetPreferences() {
        view.askUserToConfirmReset();
    }

    @Override
    public void resetPreferences() {
        firebaseAnalytics.logEvent("RESET_PREFERENCE", null);
        preferenceHelper.setNotConfigured();
        view.goToMySureddits();
    }

    @Override
    public void delegateOnShareClicked(RedditThread redditThread) {
        view.shareItem(redditThread);
    }

    @Override
    protected DiffUtil.Callback provideCustomDiffCallback(List<RedditThread> oldItems, List<RedditThread> newItems) {
        return new RedditThreadDiffCalback(oldItems, newItems);
    }

    class RedditThreadDiffCalback extends DiffUtil.Callback {

        private final List<RedditThread> oldItems;
        private final List<RedditThread> newItems;

        RedditThreadDiffCalback(List<RedditThread> oldItems, List<RedditThread> newItems) {
            this.oldItems = oldItems;
            this.newItems = newItems;
        }

        @Override
        public int getOldListSize() {
            return oldItems == null ? 0 : oldItems.size();
        }

        @Override
        public int getNewListSize() {
            return newItems == null ? 0 : newItems.size();
        }

        @Override
        public boolean areItemsTheSame(int i, int i1) {
            return oldItems.get(i).equals(newItems.get(i1));
        }

        @Override
        public boolean areContentsTheSame(int i, int i1) {
            final RedditThread old = oldItems.get(i);
            final RedditThread newThread = newItems.get(i1);

            return old.getUps() == newThread.getUps()
                    && old.getNumComments() == newThread.getNumComments();
        }
    }
}
