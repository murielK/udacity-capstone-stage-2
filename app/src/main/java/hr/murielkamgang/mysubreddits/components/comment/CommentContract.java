package hr.murielkamgang.mysubreddits.components.comment;

import android.os.Bundle;

import hr.murielkamgang.mysubreddits.components.base.BaseContentListContract;
import hr.murielkamgang.mysubreddits.data.model.comment.Comment;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;

public interface CommentContract {

    interface View extends BaseContentListContract.View<Comment> {

        void onThreadLaded(RedditThread redditThread);

        void openYoutube(String url);

        void viewImage(String url);

        void shareItem(RedditThread redditThread);

        void showEmptyText(boolean show);
    }

    interface Presenter extends BaseContentListContract.Presenter<View> {

        void delegateOnCardClicked(RedditThread redditThread);

        void delegateOnShareClicked(RedditThread redditThread);

        void setBundle(Bundle bundle);


    }
}
