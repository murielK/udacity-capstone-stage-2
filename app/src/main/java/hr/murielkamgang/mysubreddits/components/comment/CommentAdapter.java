package hr.murielkamgang.mysubreddits.components.comment;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.components.base.BaseRecyclerViewAdapter;
import hr.murielkamgang.mysubreddits.data.model.comment.Comment;
import hr.murielkamgang.mysubreddits.helper.CountHelper;
import hr.murielkamgang.mysubreddits.helper.DateHelper;

public class CommentAdapter extends BaseRecyclerViewAdapter<Comment, BaseRecyclerViewAdapter.ItemBaseVH> {

    private final CountHelper countHelper;
    private final DateHelper dateHelper;

    public CommentAdapter(CountHelper countHelper, DateHelper dateHelper) {
        this.countHelper = countHelper;
        this.dateHelper = dateHelper;
    }

    @NonNull
    @Override
    public ItemBaseVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CommentVh(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_comnent, viewGroup, false));
    }

    class CommentVh extends CommentAdapter.ItemBaseVH {

        @BindView(R.id.textAuthor)
        TextView textAuthor;
        @BindView(R.id.textDate)
        TextView textDate;
        @BindView(R.id.textContent)
        TextView textContent;
        @BindView(R.id.textUps)
        TextView textUps;
        @BindView(R.id.textDowns)
        TextView textDowns;

        CommentVh(View itemView) {
            super(itemView);
        }

        @Override
        protected void performBinding(Comment comment) {
            textAuthor.setText(comment.getAuthor());
            textDate.setText(dateHelper.showDatePretty(comment.getCreatedUtc()));
            textContent.setText(comment.getBody());
            textUps.setText(countHelper.showCountPretty(R.string.text_count, R.string.text_count, comment.getUps()));
            textDowns.setText(countHelper.showCountPretty(R.string.text_count, R.string.text_count, comment.getDowns()));
        }
    }
}
