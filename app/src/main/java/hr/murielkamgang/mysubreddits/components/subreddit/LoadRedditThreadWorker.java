package hr.murielkamgang.mysubreddits.components.subreddit;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.work.Worker;
import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.MySubredditsApplication;
import hr.murielkamgang.mysubreddits.components.widget.RedditThreadWidgetProvider;
import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.data.source.subreddit.SubredditRepository;
import hr.murielkamgang.mysubreddits.data.source.thread.RedditThreadRepository;

public class LoadRedditThreadWorker extends Worker {

    static final String WORK_TAG = "LoadRedditThreadWorker";
    private final Logger logger = LoggerFactory.getLogger(LoadRedditThreadWorker.class);
    @Inject
    RedditThreadRepository redditThreadStringRepository;
    @Inject
    SubredditRepository subredditStringRepository;

    @NonNull
    @Override
    public Result doWork() {
        logger.debug("work started");
        boolean success = false;
        final List<RedditThread> finalRedditThreads = new ArrayList<>();
        ((MySubredditsApplication) getApplicationContext()).getAppComponent().inject(this);
        try {
            final List<Subreddit> userSubreddits = subredditStringRepository.getLocalDataSource().getAllData();
            if (userSubreddits != null) {
                logger.debug("perform fetch post for: {} subreddits", userSubreddits.size());
                for (Subreddit s : userSubreddits) {
                    logger.debug("fetch subreddit {} started", s.getDisplayNamePrefixed());
                    final List<RedditThread> redditThreads = redditThreadStringRepository.getRemoteDataSource().getAllData(s.getDisplayName());
                    if (redditThreads != null) {
                        logger.debug("fetched {} thread for subreddits {}", redditThreads.size(), s.getDisplayName());
                        finalRedditThreads.addAll(redditThreads);
                    }
                }

                forWidgetUpdate();
                success = true;
            }
        } catch (Exception e) {
            logger.debug("", e);
            success = false;
        }

        if (!finalRedditThreads.isEmpty()) {
            try {
                logger.debug("saving {} to redditThreadLocalDataSource", finalRedditThreads.size());
                redditThreadStringRepository.getLocalDataSource().saveData(finalRedditThreads);
            } catch (Exception e) {
                logger.debug("", e);
                success = false;
            }
        }

        logger.debug("work ended with success? {}", success);
        return success ? Result.SUCCESS : Result.RETRY;
    }

    private void forWidgetUpdate() {
        final Context context = getApplicationContext();
        final AppWidgetManager am = AppWidgetManager.getInstance(context);
        final int[] appWidgetIds = am.getAppWidgetIds(
                new ComponentName(context, RedditThreadWidgetProvider.class));

        RedditThreadWidgetProvider.update(context, am, appWidgetIds);
        am.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.listView);
    }
}
