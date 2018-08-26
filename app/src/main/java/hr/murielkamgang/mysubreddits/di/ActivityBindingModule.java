package hr.murielkamgang.mysubreddits.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hr.murielkamgang.mysubreddits.components.comment.CommentActivity;
import hr.murielkamgang.mysubreddits.components.comment.CommentModule;
import hr.murielkamgang.mysubreddits.components.image.ImageViewerActivity;
import hr.murielkamgang.mysubreddits.components.reddithread.RedditModule;
import hr.murielkamgang.mysubreddits.components.reddithread.RedditThreadActivity;
import hr.murielkamgang.mysubreddits.components.subreddit.SubRedditActivity;
import hr.murielkamgang.mysubreddits.components.subreddit.SubredditModule;

/**
 * Created by muriel on 9/20/17.
 */

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = SubredditModule.class)
    @ActivityScoped
    abstract SubRedditActivity provideSubredditActivity();

    @ContributesAndroidInjector(modules = RedditModule.class)
    @ActivityScoped
    abstract RedditThreadActivity provideRedditThreadActivity();

    @ContributesAndroidInjector(modules = CommentModule.class)
    @ActivityScoped
    abstract CommentActivity provideCommentActivity();

    @ContributesAndroidInjector
    @ActivityScoped
    abstract ImageViewerActivity provideImageViewActivity();
}