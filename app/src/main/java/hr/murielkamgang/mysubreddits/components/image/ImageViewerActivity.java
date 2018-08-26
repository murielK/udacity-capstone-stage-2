package hr.murielkamgang.mysubreddits.components.image;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;
import hr.murielkamgang.mysubreddits.R;

/**
 * Created by muriel on 3/25/18.
 */
public class ImageViewerActivity extends DaggerAppCompatActivity {

    private static final String EXTRA_IMAGE_URL_KEY = "EXTRA_IMAGE_URL_KEY";

    @BindView(R.id.imageView)
    ImageView imageView;

    public static void viewImage(Context context, String imageUrl) {
        final Intent intent = new Intent(context, ImageViewerActivity.class);
        intent.putExtra(EXTRA_IMAGE_URL_KEY, imageUrl);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image);
        ButterKnife.bind(this);

        Picasso.get()
                .load(getIntent().getStringExtra(EXTRA_IMAGE_URL_KEY))
                .fit()
                .centerInside()
                .into(imageView);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        final int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
