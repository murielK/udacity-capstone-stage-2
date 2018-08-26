package hr.murielkamgang.mysubreddits.components.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.comment.CommentActivity;

public class RedditThreadWidgetProvider extends AppWidgetProvider {


    public static void update(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        for (final int appWidgetId : appWidgetIds) {

            RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                    R.layout.reddit_thread_widget);

            final Intent intent = new Intent(context, RedditThreadWidgetService.class);

            final Intent openThreadIntent = new Intent(context, CommentActivity.class);
            final PendingIntent startActivityPendingIntent = PendingIntent.getActivity(context, 0, openThreadIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            remoteViews.setPendingIntentTemplate(R.id.listView, startActivityPendingIntent);
            remoteViews.setRemoteAdapter(R.id.listView, intent);

            appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
        }
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        update(context, appWidgetManager, appWidgetIds);
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
