package hr.murielkamgang.mysubreddits.components.subreddit;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import javax.inject.Inject;

import butterknife.OnClick;
import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.AdapterItemDivider;
import hr.murielkamgang.mysubreddits.components.base.BaseContentFragment;
import hr.murielkamgang.mysubreddits.components.reddithread.RedditThreadActivity;
import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;

public class SubredditFragment extends BaseContentFragment<Subreddit, SubredditContract.View, SubredditContract.Presenter>
        implements SubredditContract.View {

    @Inject
    SubredditContract.Presenter presenter;
    @Inject
    SubredditAdapter subredditAdapter;

    @Inject
    public SubredditFragment() {
    }

    @Override
    protected void onItemClicked(Subreddit subreddit) {
        //Ignore
    }

    @Override
    protected void onViewClicked(View view, Subreddit subreddit) {
        //Ignore
    }

    @Override
    protected void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        final AdapterItemDivider itemDivider =
                new AdapterItemDivider(getContext(), getResources().getDrawable(R.drawable.recyclerview_item_divider), AdapterItemDivider.ORIENTATION_VERTICAL);
        recyclerView.addItemDecoration(itemDivider);
        recyclerView.setAdapter(subredditAdapter);
    }

    @Override
    protected SubredditContract.Presenter providePresenter() {
        return presenter;
    }

    @Override
    protected int getResourceView() {
        return R.layout.fragment_subreddit;
    }

    @OnClick(R.id.buttonSave)
    public void onClick(View v) {
        if (presenter != null) {
            presenter.delegateOkClicked(subredditAdapter.getSelectedIds());
        }
    }

    @Override
    public void goNext() {
        startActivity(new Intent(getContext(), RedditThreadActivity.class));
        getActivity().finish();
    }
}
