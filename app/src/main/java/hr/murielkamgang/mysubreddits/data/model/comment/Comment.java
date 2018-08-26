package hr.murielkamgang.mysubreddits.data.model.comment;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity
public class Comment {

    @PrimaryKey
    @NonNull
    private String id;
    @SerializedName("subreddit_id")
    private String subredditId;
    private long ups;
    private long downs;
    @SerializedName("link_id")
    private String linksId;
    private String author;
    @SerializedName("parent_id")
    private String parentId;
    private String body;
    private String name;
    private long created;
    @SerializedName("created_utc")
    private long createdUtc;

    public String getSubredditId() {
        return subredditId;
    }

    public void setSubredditId(String subredditId) {
        this.subredditId = subredditId;
    }

    public long getUps() {
        return ups;
    }

    public void setUps(long ups) {
        this.ups = ups;
    }

    public long getDowns() {
        return downs;
    }

    public void setDowns(long downs) {
        this.downs = downs;
    }

    public String getLinksId() {
        return linksId;
    }

    public void setLinksId(String linksId) {
        this.linksId = linksId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(long createdUtc) {
        this.createdUtc = createdUtc;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return ups == comment.ups &&
                downs == comment.downs &&
                created == comment.created &&
                createdUtc == comment.createdUtc &&
                Objects.equals(id, comment.id) &&
                Objects.equals(subredditId, comment.subredditId) &&
                Objects.equals(linksId, comment.linksId) &&
                Objects.equals(author, comment.author) &&
                Objects.equals(parentId, comment.parentId) &&
                Objects.equals(body, comment.body) &&
                Objects.equals(name, comment.name);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, subredditId, ups, downs, linksId, author, parentId, body, name, created, createdUtc);
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id='" + id + '\'' +
                ", subredditId='" + subredditId + '\'' +
                ", ups=" + ups +
                ", downs=" + downs +
                ", linksId='" + linksId + '\'' +
                ", author='" + author + '\'' +
                ", parentId='" + parentId + '\'' +
                ", body='" + body + '\'' +
                ", name='" + name + '\'' +
                ", created=" + created +
                ", createdUtc=" + createdUtc +
                '}';
    }
}

