package hr.murielkamgang.mysubreddits.components;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dagger.android.AndroidInjector;
import dagger.android.support.DaggerApplication;
import hr.murielkamgang.mysubreddits.di.AppComponent;
import hr.murielkamgang.mysubreddits.di.DaggerAppComponent;
import io.reactivex.Scheduler;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.plugins.RxJavaPlugins;

public class MySubredditsApplication extends DaggerApplication {

    private final Logger logger = LoggerFactory.getLogger(MySubredditsApplication.class);
    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        Scheduler androidMainThreadScheduler = AndroidSchedulers.from(getMainLooper(), true);
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(schedulerCallable -> androidMainThreadScheduler);
        RxJavaPlugins.setErrorHandler(throwable -> logger.error("Probably an undeliverableException: {}", throwable));
    }

    @Override
    protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
        appComponent = DaggerAppComponent.builder().application(this).build();
        appComponent.inject(this);
        return appComponent;
    }

    public AppComponent getAppComponent() {
        return appComponent;
    }
}
