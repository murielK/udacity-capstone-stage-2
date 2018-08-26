package hr.murielkamgang.mysubreddits.components.subreddit;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseFragment;
import hr.murielkamgang.mysubreddits.components.base.BaseToolBarActivity;

public class SubRedditActivity extends BaseToolBarActivity {

    @Inject
    SubredditFragment subredditFragment;

    @Override
    protected int provideLayoutRes() {
        return R.layout.base_toolbar_activity;
    }

    @Override
    protected BaseFragment provideFragment() {
        return subredditFragment;
    }

    @Override
    protected int provideToolbarTitleResId() {
        return R.string.select_subreddit;
    }

    @Override
    protected Class<? extends AppCompatActivity> provideParentActivityClass() {
        return null;
    }
}
