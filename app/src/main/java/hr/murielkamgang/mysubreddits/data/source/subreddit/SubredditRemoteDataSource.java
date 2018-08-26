package hr.murielkamgang.mysubreddits.data.source.subreddit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;
import hr.murielkamgang.mysubreddits.data.model.subreddit.SubredditChildren;
import hr.murielkamgang.mysubreddits.data.model.subreddit.SubredditRoot;
import hr.murielkamgang.mysubreddits.data.source.DataSourceException;
import hr.murielkamgang.mysubreddits.data.source.base.BaseRemoteDataSource;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class SubredditRemoteDataSource extends BaseRemoteDataSource<Subreddit, String> {

    private final Logger logger = LoggerFactory.getLogger(SubredditRemoteDataSource.class);
    private final SubredditApi subredditApi;

    @Inject
    public SubredditRemoteDataSource(Retrofit retrofit) {
        this.subredditApi = retrofit.create(SubredditApi.class);
    }

    @Override
    public List<Subreddit> getAllData() {
        try {
            final List<SubredditChildren> subredditChildrenList = subredditApi.getSubreddits(100).execute().body().data.children;
            final List<Subreddit> subreddits = new ArrayList<>(subredditChildrenList.size());
            for (SubredditChildren subredditChildren : subredditChildrenList) {
                subreddits.add(subredditChildren.data);
            }

            return subreddits;
        } catch (IOException e) {
            logger.debug("", e);

            throw new DataSourceException("getAllData()", e);
        }
    }

    @Override
    public Observable<List<Subreddit>> getAllDataAsObservable() {
        return Observable.fromCallable(this::getAllData);
    }

    interface SubredditApi {

        @GET("reddits.json")
        Call<SubredditRoot> getSubreddits(@Query("limit") int limit);

    }

}
