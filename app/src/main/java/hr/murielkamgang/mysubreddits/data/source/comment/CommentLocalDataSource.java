package hr.murielkamgang.mysubreddits.data.source.comment;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import hr.murielkamgang.mysubreddits.data.model.comment.Comment;
import hr.murielkamgang.mysubreddits.data.source.base.BaseLocalDataSource;

@Dao
public abstract class CommentLocalDataSource extends BaseLocalDataSource<Comment, String> {

    @Override
    @Query("SELECT * FROM Comment WHERE  id = :key")
    public abstract Comment getData(String key);

    @Override
    @Query("SELECT * FROM Comment WHERE  parentId = :key")
    public abstract List<Comment> getAllData(String key);

    @Override
    @Query("SELECT * FROM Comment ORDER BY created DESC")
    public abstract List<Comment> getAllData();

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveData(Comment comment);

    @Override
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void saveData(List<Comment> t);

    @Override
    @Delete
    public abstract void delete(Comment comment);

    @Override
    @Query("DELETE FROM RedditThread")
    public abstract void deleteAll();

    @Query("SELECT * FROM Comment WHERE  parentId LIKE :key")
    public abstract LiveData<List<Comment>> getAllLiveData(String key);
}
