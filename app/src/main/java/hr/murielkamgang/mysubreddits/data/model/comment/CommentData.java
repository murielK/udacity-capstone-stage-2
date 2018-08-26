package hr.murielkamgang.mysubreddits.data.model.comment;

import java.util.List;

public class CommentData {

    public String modhash;
    public String dist;
    public String after;
    public String before;
    public List<CommentChildren> children;
}
