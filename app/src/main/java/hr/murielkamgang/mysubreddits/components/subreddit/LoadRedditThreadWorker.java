package hr.murielkamgang.mysubreddits.components.subreddit;

import android.support.annotation.NonNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import androidx.work.Worker;
import hr.murielkamgang.mysubreddits.components.MySubredditsApplication;
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
        ((MySubredditsApplication) getApplicationContext()).getAppComponent().inject(this);
        logger.debug("work started");
        final List<Subreddit> userSubreddits = subredditStringRepository.getLocalDataSource().getAllData();
        if (userSubreddits != null) {
            logger.debug("perform fetch post for: {} subreddits", userSubreddits.size());
            final List<RedditThread> finalRedditThreads = new ArrayList<>();
            for (Subreddit s : userSubreddits) {
                logger.debug("fetch subreddit {} started", s.getDisplayNamePrefixed());
                final List<RedditThread> redditThreads = redditThreadStringRepository.getRemoteDataSource().getAllData(s.getDisplayName());
                if (redditThreads != null) {
                    logger.debug("fetched {} thread for subreddits {}", redditThreads.size(), s.getDisplayName());
                    finalRedditThreads.addAll(redditThreads);
                }
            }

            logger.debug("saving {} to redditThreadLocalDataSource", finalRedditThreads.size());
            redditThreadStringRepository.getLocalDataSource().saveData(finalRedditThreads);
        }

        logger.debug("work ended");
        return Result.SUCCESS;
    }
}
