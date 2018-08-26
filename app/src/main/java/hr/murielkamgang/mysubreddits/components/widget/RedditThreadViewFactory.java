package hr.murielkamgang.mysubreddits.components.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.List;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.comment.CommentActivity;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.data.source.LocalDatabase;
import hr.murielkamgang.mysubreddits.helper.DateHelper;


class RedditThreadViewFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context context;
    private final DateHelper dateHelper;
    private List<RedditThread> redditsThreads;

    RedditThreadViewFactory(Context context, DateHelper dateHelper) {
        this.context = context;
        this.dateHelper = dateHelper;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        if (redditsThreads != null) {
            redditsThreads.clear();
        }
        redditsThreads = LocalDatabase.getInstance(context).reddiThreadLocalDataSource().getAllData();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return redditsThreads == null ? 0 : redditsThreads.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.reddit_thread_widget_item);

        final RedditThread redditThread = redditsThreads.get(position);

        remoteViews.setTextViewText(R.id.textSubreddit, redditThread.getSubredditNamePrefixed());
        remoteViews.setTextViewText(R.id.textAuthor, redditThread.getAuthor());
        remoteViews.setTextViewText(R.id.textTitle, redditThread.getTitle());
        remoteViews.setTextViewText(R.id.textDuration, dateHelper.showDatePretty(redditThread.getCreatedUtc()));

        final Intent fillInIntent = new Intent();
        fillInIntent.putExtra(CommentActivity.EXTRA_THREAD_ID, redditThread.getId());
        remoteViews.setOnClickFillInIntent(R.id.rootWidgetItem, fillInIntent);

        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
