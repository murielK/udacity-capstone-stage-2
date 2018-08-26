package hr.murielkamgang.mysubreddits.components.reddithread;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import javax.inject.Inject;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseContentFragment;
import hr.murielkamgang.mysubreddits.components.comment.CommentActivity;
import hr.murielkamgang.mysubreddits.components.image.ImageViewerActivity;
import hr.murielkamgang.mysubreddits.components.subreddit.SubRedditActivity;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.util.Utils;

public class RedditThreadFragment extends BaseContentFragment<RedditThread, RedditThreadContract.View, RedditThreadContract.Presenter> implements RedditThreadContract.View {

    @Inject
    RedditThreadContract.Presenter presenter;
    @Inject
    RedditThreadApdater redditThreadApdater;

    @Inject
    public RedditThreadFragment() {
    }

    @Override
    protected void onItemClicked(RedditThread redditThread) {
        if (presenter != null) {
            presenter.delegateOnItemClicked(redditThread);
        }
    }

    @Override
    protected void onViewClicked(View view, RedditThread redditThread) {
        if (presenter != null) {
            final int id = view.getId();

            switch (id) {
                case R.id.textShare:
                    presenter.delegateOnShareClicked(redditThread);
                    break;
                case R.id.viewClick:
                    presenter.delegateOnCardClicked(redditThread);
                    break;
            }
        }
    }

    @Override
    protected void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(redditThreadApdater);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.reddit_thread_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (presenter != null && item.getItemId() == R.id.menu_reset_preference) {
            presenter.delegateResetPreferences();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected RedditThreadContract.Presenter providePresenter() {
        return presenter;
    }

    @Override
    protected int getResourceView() {
        return R.layout.default_recycler_view_fragment;
    }

    @Override
    public void viewCommentFor(RedditThread redditThread) {
        CommentActivity.viewComment(getContext(), redditThread);
    }

    @Override
    public void openYoutube(String url) {
        Utils.watchYoutubeVideo(getContext(), url);
    }

    @Override
    public void viewImage(String url) {
        ImageViewerActivity.viewImage(getContext(), url);
    }

    @Override
    public void goToMySureddits() {
        final Intent intent = new Intent(getContext(), SubRedditActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void askUserToConfirmReset() {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_reset_preference)
                .setMessage(R.string.msg_reset_preference)
                .setPositiveButton(R.string.text_yes, (dialog, which) -> {
                    if (presenter != null) {
                        presenter.resetPreferences();
                    }
                })
                .setNegativeButton(R.string.text_no, (dialog, which) -> dialog.cancel())
                .show();
    }

    @Override
    public void shareItem(RedditThread redditThread) {
        Utils.share(getContext(), redditThread);
    }
}
