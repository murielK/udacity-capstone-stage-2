package hr.murielkamgang.mysubreddits.components.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import hr.murielkamgang.mysubreddits.R;

/**
 * Created by muriel on 03/3/18.
 */

public abstract class BaseToolBarActivity extends DaggerAppCompatActivity {

    private static final String MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG";
    @Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    protected abstract int provideLayoutRes();

    protected abstract BaseFragment provideFragment();

    protected abstract int provideToolbarTitleResId();

    protected abstract Class<? extends AppCompatActivity> provideParentActivityClass();

    protected boolean isDisplayHomeAsUpEnabled() {
        return provideParentActivityClass() != null;
    }

    public Toolbar getToolbar() {
        return toolbar;
    }


    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(provideLayoutRes());

        ButterKnife.bind(this);

        if (toolbar != null) {
            setSupportActionBar(toolbar);

            getSupportActionBar().setTitle("");

            final TextView title = toolbar.findViewById(R.id.toolbar_title);
            final int titleResId = provideToolbarTitleResId();

            if (titleResId > 0) {
                title.setText(titleResId);
            }

            if (isDisplayHomeAsUpEnabled()) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }

        if (savedInstanceState == null) {
            final Fragment f = provideFragment();
            f.setHasOptionsMenu(fragmentHasMenu());
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container, f, MAIN_FRAGMENT_TAG)
                    .commit();
        } else {
            getSupportFragmentManager().findFragmentByTag(MAIN_FRAGMENT_TAG).setHasOptionsMenu(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            final Class<? extends AppCompatActivity> c = provideParentActivityClass();
            if (c == null) {
                onBackPressed();
                return true;
            }
            goTo(c);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    protected void goToParentBack() {
        final Class<? extends AppCompatActivity> c = provideParentActivityClass();
        goTo(c);
    }

    protected void goTo(final Class<? extends AppCompatActivity> c) {
        final Intent intent = new Intent(this, c);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }

    protected boolean fragmentHasMenu() {
        return false;
    }
}
