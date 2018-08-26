package hr.murielkamgang.mysubreddits.components.reddithread;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import hr.murielkamgang.mysubreddits.di.ActivityScoped;
import hr.murielkamgang.mysubreddits.di.FragmentScoped;
import hr.murielkamgang.mysubreddits.helper.CountHelper;
import hr.murielkamgang.mysubreddits.helper.DateHelper;

@Module
public abstract class RedditModule {

    @ActivityScoped
    @Provides
    static RedditThreadApdater provideRedditThreadAdaoter(DateHelper dateHelper, CountHelper countHelper) {
        return new RedditThreadApdater(dateHelper, countHelper);
    }

    @FragmentScoped
    @ContributesAndroidInjector
    abstract RedditThreadFragment provideRedditThreadFragment();

    @ActivityScoped
    @Binds
    abstract RedditThreadContract.Presenter provideRedditThreadPresenter(RedditThreadPresenter redditThreadPresenter);
}
