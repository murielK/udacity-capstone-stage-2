package hr.murielkamgang.mysubreddits.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;
import dagger.android.support.DaggerApplication;
import hr.murielkamgang.mysubreddits.components.MySubredditsApplication;
import hr.murielkamgang.mysubreddits.components.subreddit.LoadRedditThreadWorker;

/**
 * Created by muriel on 9/20/17.
 */

@Singleton
@Component(modules = {ApplicationModule.class,
        ActivityBindingModule.class,
        AndroidInjectionModule.class,
        ServiceBindingModule.class,
        AndroidSupportInjectionModule.class})
public interface AppComponent extends AndroidInjector<DaggerApplication> {

    void inject(MySubredditsApplication mySubredditsApplication);

    void inject(LoadRedditThreadWorker worker);

    @Override
    void inject(DaggerApplication instance);

    @Component.Builder
    interface Builder {

        @BindsInstance
        AppComponent.Builder application(Application application);

        AppComponent build();
    }

}
