package hr.murielkamgang.mysubreddits.data.source.comment;

import java.util.List;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.data.model.comment.Comment;
import hr.murielkamgang.mysubreddits.data.source.base.BaseLocalDataSource;
import hr.murielkamgang.mysubreddits.data.source.base.BaseRemoteDataSource;
import hr.murielkamgang.mysubreddits.data.source.base.BaseRepository;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CommentRepository extends BaseRepository<Comment, String> {

    private final CommentLocalDataSource commentLocalDataSource;
    private final CommentRemoteDataSource commentRemoteDataSource;

    @Inject
    public CommentRepository(CommentLocalDataSource commentLocalDataSource, CommentRemoteDataSource commentRemoteDataSource) {
        this.commentLocalDataSource = commentLocalDataSource;
        this.commentRemoteDataSource = commentRemoteDataSource;
    }

    @Override
    public BaseLocalDataSource<Comment, String> getLocalDataSource() {
        return commentLocalDataSource;
    }

    @Override
    public BaseRemoteDataSource<Comment, String> getRemoteDataSource() {
        return commentRemoteDataSource;
    }

    @Override
    protected Observable<List<Comment>> getAllAndSaveAsObservable(String s, boolean sync) {
        final String localKey = "t3_" + s;
        return getRemoteDataSource().getAllDataAsObservable(s)
                .subscribeOn(Schedulers.io())
                .doOnNext(ts -> handleOnNext(ts, sync))
                .flatMap(newsList -> getLocalDataSource().getAllDataAsObservable(localKey))
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Comment>> getAllDataAsObservable(String s, boolean sync) {
        final String localKey = "t3_" + s;
        return sync ? getAllAndSaveAsObservable(s, true) : getLocalDataSource()
                .getAllDataAsObservable(localKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    return Observable.empty();
                })
                .concatWith(getAllAndSaveAsObservable(s, false));
    }
}
