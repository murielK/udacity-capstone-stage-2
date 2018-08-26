package hr.murielkamgang.mysubreddits.data.source.thread;

import android.arch.lifecycle.LiveData;

import java.util.List;

import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;

public interface RedditThreadSourceEx {

    LiveData<List<RedditThread>> getAllDataLive();
}
