package hr.murielkamgang.mysubreddits.data.source.thread;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.support.annotation.NonNull;

import java.util.List;

import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.data.source.base.BaseLocalDataSource;

@Dao
public abstract class ReddiThreadLocalDataSource extends BaseLocalDataSource<RedditThread, String> implements RedditThreadSourceEx {

    @Override
    @Query("SELECT * FROM RedditThread WHERE  id = :key")
    public abstract RedditThread getData(@NonNull String key);

    @Override
    @Query("SELECT * FROM RedditThread ORDER BY created DESC")
    public abstract List<RedditThread> getAllData();

    @Override
    @Query("SELECT * FROM RedditThread WHERE  subredditNamePrefixed = :key")
    public abstract List<RedditThread> getAllData(@NonNull String key);

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveData(@NonNull RedditThread t);

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveData(@NonNull List<RedditThread> t);

    @Delete
    public abstract void delete(@NonNull RedditThread redditThread);

    @Override
    @Query("DELETE FROM RedditThread")
    public abstract void deleteAll();

    @Override
    @Query("SELECT * FROM RedditThread")
    public abstract LiveData<List<RedditThread>> getAllDataLive();
}
