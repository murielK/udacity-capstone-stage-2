package hr.murielkamgang.mysubreddits.data.source.subreddit;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;
import hr.murielkamgang.mysubreddits.data.source.base.BaseLocalDataSource;

@Dao
public abstract class SubredditLocalDataSource extends BaseLocalDataSource<Subreddit, String> {

    @Override
    @Query("SELECT * FROM Subreddit WHERE  displayNamePrefixed = :key")
    public abstract Subreddit getData(@NonNull String key);

    @Override
    @Query("SELECT * FROM Subreddit")
    public abstract List<Subreddit> getAllData();

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveData(@NonNull Subreddit t);

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveData(@NonNull List<Subreddit> t);

    @Override
    @Delete
    public abstract void delete(@NonNull Subreddit subreddit);

    @Override
    @Query("DELETE FROM RedditThread")
    public abstract void deleteAll();

    @Override
    @Query("SELECT * FROM Subreddit WHERE  subredditType = :key")
    public abstract List<Subreddit> getAllData(@NonNull String key);
}
