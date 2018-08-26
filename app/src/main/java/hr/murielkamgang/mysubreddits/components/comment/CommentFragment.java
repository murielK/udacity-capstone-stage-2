package hr.murielkamgang.mysubreddits.components.comment;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import javax.inject.Inject;

import butterknife.BindView;
import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.AdapterItemDivider;
import hr.murielkamgang.mysubreddits.components.base.BaseContentFragment;
import hr.murielkamgang.mysubreddits.components.image.ImageViewerActivity;
import hr.murielkamgang.mysubreddits.components.reddithread.ThreadBinder;
import hr.murielkamgang.mysubreddits.data.model.comment.Comment;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.helper.CountHelper;
import hr.murielkamgang.mysubreddits.helper.DateHelper;
import hr.murielkamgang.mysubreddits.util.Utils;

public class CommentFragment extends BaseContentFragment<Comment, CommentContract.View, CommentContract.Presenter> implements CommentContract.View {

    @Inject
    CommentContract.Presenter presenter;
    @Inject
    CommentAdapter commentAdapter;
    @Inject
    CountHelper countHelper;
    @Inject
    DateHelper dateHelper;

    @BindView(R.id.layoutThread)
    ConstraintLayout layoutThread;
    @BindView(R.id.separator)
    View separator;
    @BindView(R.id.textEmptyMsg)
    TextView textEmptyMsg;

    private ThreadBinder threadBinder;
    private RedditThread redditThread;

    @Inject
    public CommentFragment() {
    }

    @Override
    protected void onItemClicked(Comment comment) {

    }

    @Override
    protected void onViewClicked(View view, Comment comment) {

    }

    @Override
    protected void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final AdapterItemDivider itemDivider =
                new AdapterItemDivider(getContext(), getResources().getDrawable(R.drawable.recyclerview_item_divider), AdapterItemDivider.ORIENTATION_VERTICAL);
        recyclerView.addItemDecoration(itemDivider);
        recyclerView.setAdapter(commentAdapter);
        recyclerView.setNestedScrollingEnabled(true);
    }

    @Override
    protected CommentContract.Presenter providePresenter() {
        return presenter;
    }

    @Override
    protected int getResourceView() {
        return R.layout.comment_fragment;
    }

    @Override
    public void onThreadLaded(RedditThread redditThread) {
        this.redditThread = redditThread;
        layoutThread.setVisibility(View.VISIBLE);
        separator.setVisibility(View.VISIBLE);
        threadBinder.doBind(redditThread);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.comment_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (presenter != null && item.getItemId() == R.id.menu_share) {
            presenter.delegateOnShareClicked(redditThread);
        }
        return super.onOptionsItemSelected(item);
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
    public void shareItem(RedditThread redditThread) {
        Utils.share(getContext(), redditThread);
    }

    @Override
    public void showEmptyText(boolean show) {
        textEmptyMsg.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    protected void onPostViewCreate(View view) {
        super.onPostViewCreate(view);
        threadBinder = new ThreadBinder(layoutThread, dateHelper, countHelper);
        threadBinder.textShare.setVisibility(View.GONE);
        separator.setVisibility(View.GONE);
        threadBinder.viewClick.setOnClickListener(v -> {
            if (presenter != null) {
                presenter.delegateOnCardClicked(redditThread);
            }
        });
    }
}
