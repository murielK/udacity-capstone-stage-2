package hr.murielkamgang.mysubreddits.components.subreddit;

import android.util.SparseBooleanArray;

import hr.murielkamgang.mysubreddits.components.base.BaseContentListContract;
import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;

public interface SubredditContract {

    interface View extends BaseContentListContract.View<Subreddit> {

        void goNext();
    }

    interface Presenter extends BaseContentListContract.Presenter<View> {

        void delegateOkClicked(SparseBooleanArray data);
    }
}
