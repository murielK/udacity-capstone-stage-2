package hr.murielkamgang.mysubreddits.components.reddithread;

import hr.murielkamgang.mysubreddits.components.base.BaseContentListContract;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;

public interface RedditThreadContract {

    interface View extends BaseContentListContract.View<RedditThread> {

        void viewCommentFor(RedditThread redditThread);

        void openYoutube(String url);

        void viewImage(String url);

        void goToMySureddits();

        void askUserToConfirmReset();

        void shareItem(RedditThread redditThread);
    }

    interface Presenter extends BaseContentListContract.Presenter<View> {

        void delegateOnItemClicked(RedditThread redditThread);

        void delegateOnCardClicked(RedditThread redditThread);

        void delegateResetPreferences();

        void resetPreferences();

        void delegateOnShareClicked(RedditThread redditThread);
    }
}
