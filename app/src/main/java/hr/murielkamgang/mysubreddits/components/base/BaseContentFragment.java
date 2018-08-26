package hr.murielkamgang.mysubreddits.components.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import butterknife.BindView;
import hr.murielkamgang.mysubreddits.R;

/**
 * Created by muriel on 3/24/18.
 */
public abstract class BaseContentFragment<T, V extends BaseContentListContract.View<T>, P extends BaseContentListContract.Presenter<V>> extends BaseDialogFragment<V, P>
        implements BaseContentListContract.View<T> {

    @Nullable
    @BindView(R.id.recyclerView)
    protected RecyclerView recyclerView;
    @Nullable
    @BindView(R.id.swipeRefreshLayout)
    protected SwipeRefreshLayout swipeRefreshLayout;

    private BaseRecyclerViewAdapter<T, ? extends BaseRecyclerViewAdapter.BaseVH> adapter;

    protected abstract void onItemClicked(T t);

    protected abstract void onViewClicked(View view, T t);

    protected abstract void initRecyclerView();

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final P p = providePresenter();
        if (p != null) {
            p.setView((V) this);
        }
    }

    @Override
    protected void onPostViewCreate(View view) {
        final Resources res = getResources();

        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setColorSchemeColors(res.getColor(R.color.colorAccent));

            final P p = providePresenter();
            swipeRefreshLayout.setOnRefreshListener(() -> {
                if (p != null) {
                    p.load();
                }
            });
        }

        initRecyclerView();

        if (recyclerView.getAdapter() instanceof BaseRecyclerViewAdapter) {
            this.adapter = (BaseRecyclerViewAdapter) recyclerView.getAdapter();
            this.adapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseRecyclerViewAdapter adapter, RecyclerView.ViewHolder viewHolder, int position) {
                    onItemClicked(BaseContentFragment.this.adapter.getItemAt(position));
                }

                @Override
                public void onViewClick(BaseRecyclerViewAdapter adapter, RecyclerView.ViewHolder viewHolder, View view, int position) {
                    onViewClicked(view, BaseContentFragment.this.adapter.getItemAt(position));
                }
            });
        }
    }

    @Override
    public void onUpdateChanged(int index, int length) {
        if (adapter != null) {
            adapter.notifyItemRangeChanged(index, length);
        }
    }

    @Override
    public void onUpdateInserted(int index, int length) {
        if (adapter != null) {
            adapter.notifyItemRangeInserted(index, length);
        }
    }

    @Override
    public void onUpdateRemoved(int index, int length) {
        if (adapter != null) {
            adapter.notifyItemRangeRemoved(index, length);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        if (adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public int indexOf(Object o) {
        return 0;
    }

    @Override
    public int adapterSize() {
        if (adapter != null) {
            return adapter.getItemCount();
        }

        return 0;
    }

    @Override
    public void onLoaded(List<T> ts, DiffUtil.DiffResult diffResult) {
        if (adapter != null) {
            adapter.setItems(ts, diffResult);
        }
    }

    @Override
    public void swipeToRefresh(boolean show) {
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(show);
        }
    }

}
