package hr.murielkamgang.mysubreddits.data.source.comment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import hr.murielkamgang.mysubreddits.data.model.comment.Comment;
import hr.murielkamgang.mysubreddits.data.model.comment.CommentChildren;
import hr.murielkamgang.mysubreddits.data.model.comment.CommentRoot;
import hr.murielkamgang.mysubreddits.data.source.DataSourceException;
import hr.murielkamgang.mysubreddits.data.source.base.BaseRemoteDataSource;
import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class CommentRemoteDataSource extends BaseRemoteDataSource<Comment, String> {

    private final Logger logger = LoggerFactory.getLogger(CommentRemoteDataSource.class);
    private final CommentsApi commentApi;

    public CommentRemoteDataSource(Retrofit retrofit) {
        this.commentApi = retrofit.create(CommentsApi.class);
    }

    @Override
    public List<Comment> getAllData(String key) {
        try {
            final List<CommentRoot> commentRoots = commentApi
                    .getComments(key, "new", 100)
                    .execute()
                    .body();


            final List<Comment> results = new ArrayList<>();

            for (CommentChildren cc : commentRoots.get(1).data.children) {
                results.add(cc.data);
            }

            return results;
        } catch (IOException e) {

            logger.debug("", e);

            throw new DataSourceException("", e);
        }
    }

    @Override
    public Observable<List<Comment>> getAllDataAsObservable(String s) {
        return Observable.fromCallable(() -> getAllData(s));
    }

    interface CommentsApi {

        @GET("/comments/{postId}/new.json")
        Call<List<CommentRoot>> getComments(@Path("postId") String postId
                , @Query("sort") String sort
                , @Query("limit") int limit);
    }

}
