package hr.murielkamgang.mysubreddits.components.comment;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;
import dagger.android.ContributesAndroidInjector;
import hr.murielkamgang.mysubreddits.di.ActivityScoped;
import hr.murielkamgang.mysubreddits.di.FragmentScoped;
import hr.murielkamgang.mysubreddits.helper.CountHelper;
import hr.murielkamgang.mysubreddits.helper.DateHelper;

@Module
public abstract class CommentModule {

    @Provides
    @ActivityScoped
    static CommentAdapter provideCommentAdapter(DateHelper dateHelper, CountHelper countHelper) {
        return new CommentAdapter(countHelper, dateHelper);
    }

    @Provides
    @ActivityScoped
    static String provideThreadId(CommentActivity commentActivity) {
        return commentActivity.getIntent().getStringExtra(CommentActivity.EXTRA_THREAD_ID);
    }

    @ContributesAndroidInjector
    @FragmentScoped
    abstract CommentFragment provideCommentFragment();

    @ActivityScoped
    @Binds
    abstract CommentContract.Presenter provideCommentPresenter(CommentPresenter commentPresenter);

}
