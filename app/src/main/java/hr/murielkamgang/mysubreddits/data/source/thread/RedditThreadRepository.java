package hr.murielkamgang.mysubreddits.data.source.thread;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.data.source.base.BaseLocalDataSource;
import hr.murielkamgang.mysubreddits.data.source.base.BaseRemoteDataSource;
import hr.murielkamgang.mysubreddits.data.source.base.BaseRepository;

public class RedditThreadRepository extends BaseRepository<RedditThread, String> {

    private final ReddiThreadLocalDataSource reddiThreadLocalDataSource;
    private final RedditThreadRemoteDataSource redditThreadRemoteDataSource;

    @Inject
    public RedditThreadRepository(RedditThreadRemoteDataSource redditThreadRemoteDataSource, ReddiThreadLocalDataSource reddiThreadLocalDataSource) {
        this.redditThreadRemoteDataSource = redditThreadRemoteDataSource;
        this.reddiThreadLocalDataSource = reddiThreadLocalDataSource;
    }

    @Override
    public BaseLocalDataSource<RedditThread, String> getLocalDataSource() {
        return reddiThreadLocalDataSource;
    }

    @Override
    public BaseRemoteDataSource<RedditThread, String> getRemoteDataSource() {
        return redditThreadRemoteDataSource;
    }

}
