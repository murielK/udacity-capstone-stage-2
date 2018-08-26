package hr.murielkamgang.mysubreddits.components.reddithread;

import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseFragment;
import hr.murielkamgang.mysubreddits.components.base.BaseToolBarActivity;

public class RedditThreadActivity extends BaseToolBarActivity {

    @Inject
    RedditThreadFragment redditThreadFragment;

    @Override
    protected int provideLayoutRes() {
        return R.layout.base_toolbar_activity;
    }

    @Override
    protected BaseFragment provideFragment() {
        return redditThreadFragment;
    }

    @Override
    protected int provideToolbarTitleResId() {
        return R.string.app_name;
    }

    @Override
    protected Class<? extends AppCompatActivity> provideParentActivityClass() {
        return null;
    }

    @Override
    protected boolean fragmentHasMenu() {
        return true;
    }
}
