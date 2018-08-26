package hr.murielkamgang.mysubreddits.di;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import hr.murielkamgang.mysubreddits.components.widget.RedditThreadWidgetService;

@Module
abstract class ServiceBindingModule {

    @ContributesAndroidInjector
    @ServiceScoped
    abstract RedditThreadWidgetService provideRedditThreadWidgetService();
}
