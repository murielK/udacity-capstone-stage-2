package hr.murielkamgang.mysubreddits.data.source;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import hr.murielkamgang.mysubreddits.data.model.comment.Comment;
import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.data.source.comment.CommentLocalDataSource;
import hr.murielkamgang.mysubreddits.data.source.subreddit.SubredditLocalDataSource;
import hr.murielkamgang.mysubreddits.data.source.thread.ReddiThreadLocalDataSource;

@Database(entities = {Subreddit.class, RedditThread.class, Comment.class}, version = 3, exportSchema = false)
public abstract class LocalDatabase extends RoomDatabase {

    private static LocalDatabase instance;

    public static LocalDatabase getInstance(Context context) {
        if (instance == null) {
            synchronized (LocalDatabase.class) {
                if (instance == null) {
                    instance = Room.databaseBuilder(context, LocalDatabase.class, "db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }

        return instance;
    }

    public abstract SubredditLocalDataSource subredditLocalDataSource();

    public abstract ReddiThreadLocalDataSource reddiThreadLocalDataSource();

    public abstract CommentLocalDataSource commentLocalDataSource();
}
