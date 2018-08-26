package hr.murielkamgang.mysubreddits.components.widget;

import android.content.Intent;
import android.widget.RemoteViewsService;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import hr.murielkamgang.mysubreddits.helper.DateHelper;

public class RedditThreadWidgetService extends RemoteViewsService {

    @Inject
    DateHelper dateHelper;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new RedditThreadViewFactory(this, dateHelper);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidInjection.inject(this);
    }
}
