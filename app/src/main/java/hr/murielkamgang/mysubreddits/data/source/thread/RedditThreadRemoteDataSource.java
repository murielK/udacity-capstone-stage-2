package hr.murielkamgang.mysubreddits.data.source.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThreadChildren;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThreadRoot;
import hr.murielkamgang.mysubreddits.data.source.DataSourceException;
import hr.murielkamgang.mysubreddits.data.source.base.BaseRemoteDataSource;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RedditThreadRemoteDataSource extends BaseRemoteDataSource<RedditThread, String> {

    private final Logger logger = LoggerFactory.getLogger(RedditThreadRemoteDataSource.class);
    private final RedditThreadApi redditThreadApi;

    public RedditThreadRemoteDataSource(Retrofit retrofit) {
        this.redditThreadApi = retrofit.create(RedditThreadApi.class);
    }

    @Override
    public RedditThread getData(String s) {
        try {
            final List<RedditThreadRoot> redditThreadRoots = redditThreadApi.getredditsFor(s, 1).execute().body();

            return redditThreadRoots.get(0).data.children.get(0).data;
        } catch (IOException e) {
            logger.debug("", e);

            throw new DataSourceException("getData", e);
        }
    }

    @Override
    public Observable<RedditThread> getDataAsObservable(String s) {
        return Observable.fromCallable(() -> getData(s));
    }

    @Override
    public Observable<List<RedditThread>> getAllDataAsObservable(String key) {
        return Observable.fromCallable(() -> getAllData(key));
    }

    @Override
    public List<RedditThread> getAllData(String key) {
        try {
            final List<RedditThreadChildren> redditThreadChildren = redditThreadApi
                    .getredditsFor(key, "new", 25)
                    .execute()
                    .body()
                    .data
                    .children;

            final List<RedditThread> results = new ArrayList<>(redditThreadChildren.size());

            for (RedditThreadChildren rc : redditThreadChildren) {
                results.add(rc.data);
            }

            return results;
        } catch (IOException e) {

            logger.debug("", e);

            throw new DataSourceException("", e);
        }
    }

    interface RedditThreadApi {

        @GET("r/{subreddit}/new.json")
        Call<RedditThreadRoot> getredditsFor(@Path("subreddit") String subreddit, @Query("sort") String sort, @Query("limit") int limit);

        @GET("/comments/{postId}/new.json")
        Call<List<RedditThreadRoot>> getredditsFor(@Path("postId") String postId, @Query("limit") int limit);

    }
}
