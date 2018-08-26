package hr.murielkamgang.mysubreddits.components.comment;

import android.support.v4.util.Pair;
import android.support.v7.util.DiffUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.components.base.BaseContentListPresenter;
import hr.murielkamgang.mysubreddits.data.model.comment.Comment;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.data.source.comment.CommentRepository;
import hr.murielkamgang.mysubreddits.data.source.thread.RedditThreadRepository;
import hr.murielkamgang.mysubreddits.util.Utils;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class CommentPresenter extends BaseContentListPresenter<Comment, CommentContract.View> implements CommentContract.Presenter {

    private final Logger logger = LoggerFactory.getLogger(CommentPresenter.class);
    private final CommentRepository commentRepository;
    private final RedditThreadRepository redditThreadRepository;
    private final String threadId;

    private Disposable threadDisposable;

    @Inject
    CommentPresenter(CommentRepository commentRepository, RedditThreadRepository redditThreadRepository, String threadId) {
        this.commentRepository = commentRepository;
        this.redditThreadRepository = redditThreadRepository;
        this.threadId = threadId;
    }

    @Override
    protected Observable<List<Comment>> provideLoadObservable(boolean sync) {
        return commentRepository.getAllDataAsObservable(threadId);
    }

    @Override
    public void setView(CommentContract.View view) {
        super.setView(view);
        loadThread();
    }

    @Override
    public void onDestroy() {
        if (threadDisposable != null) {
            threadDisposable.dispose();
        }

        super.onDestroy();
    }

    private void loadThread() {
        threadDisposable = redditThreadRepository.getDataAsObservable(threadId)
                .subscribe(redditThread -> {
                    if (view != null) {
                        view.onThreadLaded(redditThread);
                    }
                }, throwable -> logger.debug("", throwable));
    }

    @Override
    public void delegateOnCardClicked(RedditThread redditThread) {
        final String youtubeVideoKey;
        if ((youtubeVideoKey = Utils.getYoutubeKey(redditThread)) != null) {
            view.openYoutube(youtubeVideoKey);
        } else {
            view.viewImage(redditThread.getPreview().getImages().get(0).getSource().getUrl());
        }
    }

    @Override
    public void delegateOnShareClicked(RedditThread redditThread) {
        view.shareItem(redditThread);
    }

    @Override
    protected void handleNewItems(Pair<List<Comment>, DiffUtil.DiffResult> pair, boolean sync) {
        super.handleNewItems(pair, sync);
        view.showEmptyText(pair.first == null || pair.first.isEmpty());
    }
}
