package hr.murielkamgang.mysubreddits.components.subreddit;

import android.support.annotation.NonNull;
import android.support.design.chip.Chip;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;
import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseRecyclerViewAdapter;
import hr.murielkamgang.mysubreddits.data.model.subreddit.Subreddit;
import hr.murielkamgang.mysubreddits.util.RoundCornersTransformation;

public class SubredditAdapter extends BaseRecyclerViewAdapter<Subreddit, SubredditAdapter.SubredditVh> {

    @NonNull
    @Override
    public SubredditVh onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new SubredditVh(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subreddit, viewGroup, false));
    }

    class SubredditVh extends BaseRecyclerViewAdapter<Subreddit, SubredditAdapter.SubredditVh>.ItemBaseVH {

        @BindView(R.id.checkBox)
        CheckBox checkBox;
        @BindView(R.id.textTitle)
        Chip textTitle;
        @BindView(R.id.textDescription)
        TextView textDescription;
        @BindView(R.id.imageIcon)
        ImageView imageIcon;

        SubredditVh(View itemView) {
            super(itemView);
        }

        @Override
        protected void performBinding(Subreddit subreddit) {
            if (checkBox.isClickable()) {
                checkBox.setClickable(false);
            }

            if (textTitle.isClickable()) {
                textTitle.setClickable(false);
            }

            checkBox.setChecked(isSelected(getAdapterPosition()));

            textTitle.setText(subreddit.getDisplayNamePrefixed());
            textDescription.setText(subreddit.getDescription());

            if (subreddit.getIconImg() != null && !subreddit.getIconImg().isEmpty()) {
                Picasso.get()
                        .load(subreddit.getIconImg())
                        .fit()
                        .centerCrop()
                        .transform(new RoundCornersTransformation())
                        .into(imageIcon);
            } else {
                imageIcon.setImageDrawable(null);
            }
        }

        @OnClick(R.id.constrainLayout)
        void onClick(View v) {
            toggleMultiSelection(getAdapterPosition());
        }
    }
}
