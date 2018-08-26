package hr.murielkamgang.mysubreddits.components.reddithread;

import android.support.annotation.Nullable;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import hr.murielkamgang.mysubreddits.components.comment.CommentAdapter;
import hr.murielkamgang.mysubreddits.components.comment.CommentContract;
import hr.murielkamgang.mysubreddits.components.comment.CommentFragment;
import hr.murielkamgang.mysubreddits.components.comment.CommentPresenter;
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

    @ActivityScoped
    @Provides
    @Nullable
    static String provideNullableString() {
        return null;
    }

    @Provides
    @ActivityScoped
    static CommentAdapter provideCommentAdapter(DateHelper dateHelper, CountHelper countHelper) {
        return new CommentAdapter(countHelper, dateHelper);
    }

    @ContributesAndroidInjector
    @FragmentScoped
    abstract CommentFragment provideCommentFragment();

    @ActivityScoped
    @Binds
    abstract CommentContract.Presenter provideCommentPresenter(CommentPresenter commentPresenter);

}
