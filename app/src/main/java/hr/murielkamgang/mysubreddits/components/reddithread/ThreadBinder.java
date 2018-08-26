package hr.murielkamgang.mysubreddits.components.reddithread;

import android.support.constraint.ConstraintLayout;
import android.support.design.card.MaterialCardView;
import android.support.design.chip.Chip;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.data.model.thread.Image;
import hr.murielkamgang.mysubreddits.data.model.thread.RedditThread;
import hr.murielkamgang.mysubreddits.helper.CountHelper;
import hr.murielkamgang.mysubreddits.helper.DateHelper;
import hr.murielkamgang.mysubreddits.util.Utils;

public class ThreadBinder {

    public static final String UPDATE_UPS = "UPDATE_UPS";
    public static final String UPDATE_COMMENTS_COUNT = "UPDATE_COMMENTS_COUNT";
    private final Logger logger = LoggerFactory.getLogger(ThreadBinder.class);
    @BindView(R.id.layoutThread)
    public ConstraintLayout layoutThread;
    @BindView(R.id.imagePreview)
    public ImageView imagePreview;
    @BindView(R.id.textTitle)
    public TextView textTitle;
    @BindView(R.id.textComments)
    public TextView textComments;
    @BindView(R.id.textShare)
    public TextView textShare;
    @BindView(R.id.textUps)
    public TextView textUps;
    @BindView(R.id.textDowns)
    public TextView textDowns;
    @BindView(R.id.textSubreddit)
    public Chip textSubreddit;
    @BindView(R.id.textDate)
    public TextView textDate;
    @BindView(R.id.textAuthor)
    public TextView textAuthor;
    @BindView(R.id.imgPlay)
    public ImageView imgPlay;

    @BindView(R.id.card)
    public MaterialCardView cardView;
    @BindView(R.id.viewSeparator)
    public View viewSeparator;
    @BindView(R.id.viewClick)
    public View viewClick;

    private CountHelper countHelper;
    private DateHelper dateHelper;


    public ThreadBinder(View target, DateHelper dateHelper, CountHelper countHelper) {
        ButterKnife.bind(this, target);
        this.countHelper = countHelper;
        this.dateHelper = dateHelper;
    }

    public void doBind(RedditThread redditThread) {
        if (hasImage(redditThread)) {

            final Image image = redditThread.getPreview().getImages().get(0);
            if (cardView.getVisibility() != View.VISIBLE) {
                cardView.setVisibility(View.VISIBLE);
            }

            Picasso.get()
                    .load(image.getSource().getUrl())
                    .fit()
                    .centerCrop()
                    .into(imagePreview);
        } else {
            cardView.setVisibility(View.GONE);
        }

        textTitle.setText(redditThread.getTitle());
        textSubreddit.setText(redditThread.getSubredditNamePrefixed());
        textUps.setText(countHelper.showCountPretty(R.string.text_count, R.string.text_count, redditThread.getUps()));
        textDowns.setText(countHelper.showCountPretty(R.string.text_count, R.string.text_count, redditThread.getDowns()));
        textDate.setText(dateHelper.showDatePretty(redditThread.getCreatedUtc()));
        textComments.setText(countHelper.showCountPretty(R.string.text_comment, R.string.text_comments, redditThread.getNumComments()));
        textAuthor.setText(redditThread.getAuthor());

        if (Utils.isThreadIsYoutube(redditThread)) {
            imgPlay.setVisibility(View.VISIBLE);
        } else {
            imgPlay.setVisibility(View.GONE);
        }
    }

    public void doBind(RedditThread redditThread, List<Object> payload) {
        if (payload.isEmpty()) {
            doBind(redditThread);
        } else {

            logger.debug("doBind with payload {}", payload);
            for (Object o : payload) {
                if (UPDATE_UPS.equals(o.toString())) {
                    textUps.setText(countHelper.showCountPretty(R.string.text_count, R.string.text_count, redditThread.getUps()));
                } else {
                    textComments.setText(countHelper.showCountPretty(R.string.text_comment, R.string.text_comments, redditThread.getNumComments()));
                }
            }
        }
    }

    public boolean hasImage(RedditThread redditThread) {
        final List<Image> images = redditThread.getPreview() == null ? null : redditThread.getPreview().getImages();
        return images != null && images.get(0).getSource() != null;
    }
}
