package hr.murielkamgang.mysubreddits.util;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseView;
import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;


/**
 * Created by muriel on 3/4/18.
 */

public class Utils {

    private static final Logger logger = LoggerFactory.getLogger(Utils.class);

    private Utils() {
    }

    public static boolean isConnected(BaseView baseView) {
        final Context context = baseView == null ? null : baseView.getContext();
        if (context == null) {
            return false;
        }
        final NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static boolean isConnected(Context context) {
        final NetworkInfo activeNetworkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnectedOrConnecting();
    }

    public static void watchYoutubeVideo(Context context, String key) {
        final Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + key));
        if (youtubeIntent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(youtubeIntent);
        } else {
            context.startActivity(new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v=" + key)));
        }
    }

    public static boolean isThreadIsYoutube(RedditThread redditThread) {
        final String domain = redditThread.getDomain();
        return (redditThread.getUrl() != null && domain != null && (domain.startsWith("youtu.be") || domain.startsWith("youtube")));
    }

    public static String getYoutubeKey(RedditThread redditThread) {
        if (isThreadIsYoutube(redditThread)) {
            return Uri.parse(redditThread.getUrl()).getLastPathSegment();
        }

        return null;
    }

    public static void share(Context context, RedditThread redditThread) {
        final Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, redditThread.getTitle()
                + "\n"
                + "https://reddit" + redditThread.getPermalink());
        context.startActivity(Intent.createChooser(sharingIntent, context.getString(R.string.title_intent_chooser)));
    }

    public static void trackSelectedSubreddit(FirebaseAnalytics firebaseAnalytics, Subreddit subreddit) {
        final Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, subreddit.getDisplayNamePrefixed());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, subreddit.getDisplayName());
        bundle.putString(FirebaseAnalytics.Param.CONTENT, "subreddit");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public static void trackSelectRedditThread(FirebaseAnalytics firebaseAnalytics, RedditThread redditThread) {
        final Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, redditThread.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, redditThread.getSubreddit());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, redditThread.getName());
        bundle.putString(FirebaseAnalytics.Param.CONTENT, "redditThread");
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    public static void trackViewRedditThread(FirebaseAnalytics firebaseAnalytics, RedditThread redditThread, String content) {
        final Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, redditThread.getId());
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, redditThread.getSubreddit());
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, redditThread.getName());
        if (content != null) {
            bundle.putString(FirebaseAnalytics.Param.CONTENT, content);
        }
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.VIEW_ITEM, bundle);
    }
}
