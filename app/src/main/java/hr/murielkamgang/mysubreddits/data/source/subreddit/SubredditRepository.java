package hr.murielkamgang.mysubreddits.data.source.subreddit;

import java.util.List;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;
import hr.murielkamgang.mysubreddits.data.source.base.BaseLocalDataSource;
import hr.murielkamgang.mysubreddits.data.source.base.BaseRemoteDataSource;
import hr.murielkamgang.mysubreddits.data.source.base.BaseRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SubredditRepository extends BaseRepository<Subreddit, String> {

    private final SubredditRemoteDataSource subredditRemoteDataSource;
    private final SubredditLocalDataSource subredditLocalDataSource;

    @Inject
    public SubredditRepository(SubredditRemoteDataSource subredditRemoteDataSource, SubredditLocalDataSource subredditLocalDataSource) {
        this.subredditRemoteDataSource = subredditRemoteDataSource;
        this.subredditLocalDataSource = subredditLocalDataSource;
    }


    @Override
    public BaseLocalDataSource<Subreddit, String> getLocalDataSource() {
        return subredditLocalDataSource;
    }

    @Override
    public BaseRemoteDataSource<Subreddit, String> getRemoteDataSource() {
        return subredditRemoteDataSource;
    }

    @Override
    public Observable<List<Subreddit>> getAllDataAsObservable() {
        return subredditRemoteDataSource.getAllDataAsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    protected Observable<List<Subreddit>> getAllAndSaveAsObservable(boolean sync) {
        return getAllDataAsObservable();
    }

    @Override
    protected Observable<List<Subreddit>> getAllAndSaveAsObservable(String s, boolean sync) {
        return super.getAllAndSaveAsObservable(s, sync);
    }

    @Override
    public Observable<List<Subreddit>> getAllDataAsObservable(String s, boolean sync) {
        return super.getAllDataAsObservable(s, sync);
    }
}
