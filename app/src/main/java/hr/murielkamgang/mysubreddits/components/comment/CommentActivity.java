package hr.murielkamgang.mysubreddits.components.comment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseFragment;
import hr.murielkamgang.mysubreddits.components.base.BaseToolBarActivity;
import hr.murielkamgang.mysubreddits.components.reddithread.RedditThreadActivity;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;

public class CommentActivity extends BaseToolBarActivity {

    public static final String EXTRA_THREAD_ID = "EXTRA_THREAD_ID";

    @Inject
    CommentFragment commentFragment;

    public static void viewComment(Context context, RedditThread redditThread) {
        final Intent intent = new Intent(context, CommentActivity.class);
        intent.putExtra(EXTRA_THREAD_ID, redditThread.getId());
        context.startActivity(intent);
    }

    @Override
    protected int provideLayoutRes() {
        return R.layout.base_toolbar_activity;
    }

    @Override
    protected BaseFragment provideFragment() {
        return commentFragment;
    }

    @Override
    protected int provideToolbarTitleResId() {
        return 0;
    }

    @Override
    protected Class<? extends AppCompatActivity> provideParentActivityClass() {
        return RedditThreadActivity.class;
    }

    @Override
    protected boolean fragmentHasMenu() {
        return true;
    }
}
