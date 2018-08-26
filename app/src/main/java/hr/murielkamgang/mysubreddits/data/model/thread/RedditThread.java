package hr.murielkamgang.mysubreddits.data.model.thread;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity
public class RedditThread {

    @PrimaryKey
    @NonNull
    private String id;
    private String subreddit;
    private String title;
    @SerializedName("subreddit_name_prefixed")
    private String subredditNamePrefixed;
    private String name;
    @SerializedName("subreddit_type")
    private String subredditType;
    private String domain;
    private String thumbnail;
    private long created;
    private String author;
    private String url;
    @SerializedName("created_utc")
    private long createdUtc;
    @SerializedName("subreddit_subscribers")
    private String subredditSubscribers;
    @SerializedName("num_comments")
    private long numComments;
    private long ups;
    private long downs;
    @Embedded
    private Preview preview;

    private String permalink;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getSubreddit() {
        return subreddit;
    }

    public void setSubreddit(String subreddit) {
        this.subreddit = subreddit;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubredditNamePrefixed() {
        return subredditNamePrefixed;
    }

    public void setSubredditNamePrefixed(String subredditNamePrefixed) {
        this.subredditNamePrefixed = subredditNamePrefixed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubredditType() {
        return subredditType;
    }

    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getCreatedUtc() {
        return createdUtc;
    }

    public void setCreatedUtc(long createdUtc) {
        this.createdUtc = createdUtc;
    }

    public String getSubredditSubscribers() {
        return subredditSubscribers;
    }

    public void setSubredditSubscribers(String subredditSubscribers) {
        this.subredditSubscribers = subredditSubscribers;
    }

    public long getNumComments() {
        return numComments;
    }

    public void setNumComments(long numComments) {
        this.numComments = numComments;
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

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }

    public String getPermalink() {
        return permalink;
    }

    public void setPermalink(String permalink) {
        this.permalink = permalink;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RedditThread that = (RedditThread) o;
        return created == that.created &&
                createdUtc == that.createdUtc &&
                numComments == that.numComments &&
                ups == that.ups &&
                downs == that.downs &&
                Objects.equals(id, that.id) &&
                Objects.equals(subreddit, that.subreddit) &&
                Objects.equals(title, that.title) &&
                Objects.equals(subredditNamePrefixed, that.subredditNamePrefixed) &&
                Objects.equals(name, that.name) &&
                Objects.equals(subredditType, that.subredditType) &&
                Objects.equals(domain, that.domain) &&
                Objects.equals(thumbnail, that.thumbnail) &&
                Objects.equals(author, that.author) &&
                Objects.equals(url, that.url) &&
                Objects.equals(subredditSubscribers, that.subredditSubscribers) &&
                Objects.equals(preview, that.preview) &&
                Objects.equals(permalink, that.permalink);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, subreddit, title, subredditNamePrefixed, name, subredditType, domain, thumbnail, created, author, url, createdUtc, subredditSubscribers, numComments, ups, downs, preview, permalink);
    }

    @Override
    public String toString() {
        return "RedditThread{" +
                "id='" + id + '\'' +
                ", subreddit='" + subreddit + '\'' +
                ", title='" + title + '\'' +
                ", subredditNamePrefixed='" + subredditNamePrefixed + '\'' +
                ", name='" + name + '\'' +
                ", subredditType='" + subredditType + '\'' +
                ", domain='" + domain + '\'' +
                ", thumbnail='" + thumbnail + '\'' +
                ", created=" + created +
                ", author='" + author + '\'' +
                ", url='" + url + '\'' +
                ", createdUtc=" + createdUtc +
                ", subredditSubscribers='" + subredditSubscribers + '\'' +
                ", numComments=" + numComments +
                ", ups=" + ups +
                ", downs=" + downs +
                ", preview=" + preview +
                ", permalink='" + permalink + '\'' +
                '}';
    }
}
