package hr.murielkamgang.mysubreddits.components.reddithread;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseRecyclerViewAdapter;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.helper.CountHelper;
import hr.murielkamgang.mysubreddits.helper.DateHelper;

public class RedditThreadApdater extends BaseRecyclerViewAdapter<RedditThread, RedditThreadApdater.RedditVh> {

    private final DateHelper dateHelper;
    private final CountHelper countHelper;

    RedditThreadApdater(DateHelper dateHelper, CountHelper countHelper) {
        this.dateHelper = dateHelper;
        this.countHelper = countHelper;
    }

    @NonNull
    @Override
    public RedditVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new RedditVh(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.reddit_thread_item, viewGroup, false));
    }

    class RedditVh extends BaseRecyclerViewAdapter<RedditThread, RedditThreadApdater.RedditVh>.ItemBaseVH implements View.OnClickListener {

        private ThreadBinder threadBinder;

        RedditVh(View itemView) {
            super(itemView);

            threadBinder = new ThreadBinder(itemView, dateHelper, countHelper);
            threadBinder.layoutThread.setOnClickListener(this);
            threadBinder.viewClick.setOnClickListener(this);
            threadBinder.textShare.setOnClickListener(this);
        }

        @Override
        protected void performBinding(RedditThread redditThread) {
        }

        @Override
        protected void performBinding(RedditThread redditThread, List<Object> payload) {
            threadBinder.doBind(redditThread, payload);
            if (getAdapterPosition() == 0) {
                threadBinder.viewSeparator.setVisibility(View.GONE);
            } else if (threadBinder.hasImage(redditThread) && threadBinder.hasImage(getItemAt(getAdapterPosition() - 1))) {
                threadBinder.viewSeparator.setVisibility(View.GONE);
            } else {
                threadBinder.viewSeparator.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View v) {
            if (listener != null) {
                switch (v.getId()) {
                    case R.id.viewClick:
                    case R.id.textShare:
                        listener.onViewClick(RedditThreadApdater.this, this, v, getAdapterPosition());
                        break;
                    case R.id.layoutThread:
                        listener.onItemClick(RedditThreadApdater.this, this, getAdapterPosition());
                        break;

                }
            }
        }
    }
}
