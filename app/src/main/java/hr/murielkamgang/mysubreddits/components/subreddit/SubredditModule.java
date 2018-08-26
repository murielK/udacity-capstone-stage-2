package hr.murielkamgang.mysubreddits.components.subreddit;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import hr.murielkamgang.mysubreddits.data.source.subreddit.SubredditRemoteDataSource;
import hr.murielkamgang.mysubreddits.di.ActivityScoped;
import hr.murielkamgang.mysubreddits.di.FragmentScoped;
import retrofit2.Retrofit;

@Module
public abstract class SubredditModule {

    @Provides
    @ActivityScoped
    static SubredditAdapter provideSuredditAdapter() {
        return new SubredditAdapter();
    }

    @Provides
    @ActivityScoped
    static SubredditRemoteDataSource provideSubredditRemoteDataSource(Retrofit retrofit) {
        return new SubredditRemoteDataSource(retrofit);
    }

    @ContributesAndroidInjector
    @FragmentScoped
    abstract SubredditFragment provideSubredditFragment();

    @Binds
    @ActivityScoped
    abstract SubredditContract.Presenter provideSubredditPreseneter(SubredditPresenter subredditPresenter);

}
