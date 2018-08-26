package hr.murielkamgang.mysubreddits.di;

import android.app.Application;
import android.content.Context;

import com.google.firebase.analytics.FirebaseAnalytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import hr.murielkamgang.mysubreddits.BuildConfig;
import hr.murielkamgang.mysubreddits.data.source.LocalDatabase;
import hr.murielkamgang.mysubreddits.data.source.comment.CommentLocalDataSource;
import hr.murielkamgang.mysubreddits.data.source.comment.CommentRemoteDataSource;
import hr.murielkamgang.mysubreddits.data.source.comment.CommentRepository;
import hr.murielkamgang.mysubreddits.data.source.subreddit.SubredditLocalDataSource;
import hr.murielkamgang.mysubreddits.data.source.subreddit.SubredditRemoteDataSource;
import hr.murielkamgang.mysubreddits.data.source.subreddit.SubredditRepository;
import hr.murielkamgang.mysubreddits.data.source.thread.ReddiThreadLocalDataSource;
import hr.murielkamgang.mysubreddits.data.source.thread.RedditThreadRemoteDataSource;
import hr.murielkamgang.mysubreddits.data.source.thread.RedditThreadRepository;
import hr.murielkamgang.mysubreddits.helper.CountHelper;
import hr.murielkamgang.mysubreddits.helper.DateHelper;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by muriel on 9/20/17.
 */

@Module
public abstract class ApplicationModule {

    private final static boolean ENABLE_DEBUG = false;
    private static final int CACHE_SIZE = 1024 * 1024 * 12; //12MB
    private static final Logger logger = LoggerFactory.getLogger(ApplicationModule.class);

    @Singleton
    @Provides
    static OkHttpClient provideOkHttpClient(Context context) {
        final File cacheFile = new File(context.getCacheDir(), "okHttpClient");
        logger.debug("cacheFile: {}", cacheFile);

        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(logger::debug);

        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.HEADERS);
        return new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .cache(new Cache(cacheFile, CACHE_SIZE))
                .addInterceptor(httpLoggingInterceptor)
                .build();
    }

    @Singleton
    @Provides
    static Retrofit provideRetrofit(OkHttpClient okHttpClient) {
        return new Retrofit.Builder()
                .baseUrl("https://www.reddit.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();
    }

    @Singleton
    @Provides
    static FirebaseAnalytics provideFirebaseAnalytics(Context context) {
        return FirebaseAnalytics.getInstance(context);
    }

    @Singleton
    @Provides
    static DateHelper provideDateHelper(Context context) {
        return new DateHelper(context);
    }

    @Singleton
    @Provides
    static CountHelper provideCountHelper(Context context) {
        return new CountHelper(context);
    }

    @Provides
    @Singleton
    static LocalDatabase provideLocalDatabase(Context context) {
        return LocalDatabase.getInstance(context);
    }

    @Provides
    @Singleton
    static SubredditLocalDataSource provideSubredditLocalDataSource(LocalDatabase localDatabase) {
        return localDatabase.subredditLocalDataSource();
    }

    @Provides
    @Singleton
    static SubredditRepository provideSubredditRepository(SubredditRemoteDataSource subredditRemoteDataSource, SubredditLocalDataSource subredditLocalDataSource) {
        return new SubredditRepository(subredditRemoteDataSource, subredditLocalDataSource);
    }

    @Provides
    @Singleton
    static ReddiThreadLocalDataSource provideredditLocalDataSource(LocalDatabase localDatabase) {
        return localDatabase.reddiThreadLocalDataSource();
    }

    @Provides
    @Singleton
    static RedditThreadRemoteDataSource provideRedditThreadRemoteDataSource(Retrofit retrofit) {
        return new RedditThreadRemoteDataSource(retrofit);
    }

    @Provides
    @Singleton
    static RedditThreadRepository provideRedditThreadRepository(ReddiThreadLocalDataSource reddiThreadLocalDataSource, RedditThreadRemoteDataSource redditThreadRemoteDataSource) {
        return new RedditThreadRepository(redditThreadRemoteDataSource, reddiThreadLocalDataSource);
    }

    @Provides
    @Singleton
    static CommentLocalDataSource provideCommentLocalDataSource(LocalDatabase localDatabase) {
        return localDatabase.commentLocalDataSource();
    }

    @Provides
    @Singleton
    static CommentRemoteDataSource provideCommentRemoteDataSource(Retrofit retrofit) {
        return new CommentRemoteDataSource(retrofit);
    }

    @Provides
    @Singleton
    static CommentRepository provideCommentRepository(CommentLocalDataSource commentLocalDataSource, CommentRemoteDataSource commentRemoteDataSource) {
        return new CommentRepository(commentLocalDataSource, commentRemoteDataSource);
    }

    @Binds
    abstract Context bindContext(Application application);

}
