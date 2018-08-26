package hr.murielkamgang.mysubreddits.data.model.subreddit;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;

@Entity
public class Subreddit {

    @SerializedName("banner_img")
    private String bannerImg;
    @PrimaryKey
    @NonNull
    @SerializedName("display_name_prefixed")
    private String displayNamePrefixed;
    @SerializedName("display_name")
    private String displayName;
    private String title;
    @SerializedName("header_img")
    private String headerImg;
    @SerializedName("audience_target")
    private String audienceTarget;
    @SerializedName("icon_img")
    private String iconImg;
    @SerializedName("header_title")
    private String headerTitle;
    private String description;
    @SerializedName("submit_text")
    private String submitText;
    private long subscribers;
    @SerializedName("key_color")
    private String keyColor;
    @SerializedName("primary_color")
    private String primaryColor;
    private String url;
    @SerializedName("subreddit_type")
    private String subredditType;
    private double created;

    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    public String getDisplayNamePrefixed() {
        return displayNamePrefixed;
    }

    public void setDisplayNamePrefixed(String displayNamePrefixed) {
        this.displayNamePrefixed = displayNamePrefixed;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeaderImg() {
        return headerImg;
    }

    public void setHeaderImg(String headerImg) {
        this.headerImg = headerImg;
    }

    public String getAudienceTarget() {
        return audienceTarget;
    }

    public void setAudienceTarget(String audienceTarget) {
        this.audienceTarget = audienceTarget;
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    public String getHeaderTitle() {
        return headerTitle;
    }

    public void setHeaderTitle(String headerTitle) {
        this.headerTitle = headerTitle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSubmitText() {
        return submitText;
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    public long getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(long subscribers) {
        this.subscribers = subscribers;
    }

    public String getKeyColor() {
        return keyColor;
    }

    public void setKeyColor(String keyColor) {
        this.keyColor = keyColor;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSubredditType() {
        return subredditType;
    }

    public void setSubredditType(String subredditType) {
        this.subredditType = subredditType;
    }

    public String getPrimaryColor() {
        return primaryColor;
    }

    public void setPrimaryColor(String primaryColor) {
        this.primaryColor = primaryColor;
    }

    public double getCreated() {
        return created;
    }

    public void setCreated(double created) {
        this.created = created;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Subreddit subreddit = (Subreddit) o;
        return subscribers == subreddit.subscribers &&
                Double.compare(subreddit.created, created) == 0 &&
                Objects.equals(bannerImg, subreddit.bannerImg) &&
                Objects.equals(displayNamePrefixed, subreddit.displayNamePrefixed) &&
                Objects.equals(displayName, subreddit.displayName) &&
                Objects.equals(title, subreddit.title) &&
                Objects.equals(headerImg, subreddit.headerImg) &&
                Objects.equals(audienceTarget, subreddit.audienceTarget) &&
                Objects.equals(iconImg, subreddit.iconImg) &&
                Objects.equals(headerTitle, subreddit.headerTitle) &&
                Objects.equals(description, subreddit.description) &&
                Objects.equals(submitText, subreddit.submitText) &&
                Objects.equals(keyColor, subreddit.keyColor) &&
                Objects.equals(primaryColor, subreddit.primaryColor) &&
                Objects.equals(url, subreddit.url) &&
                Objects.equals(subredditType, subreddit.subredditType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bannerImg, displayNamePrefixed, displayName, title, headerImg, audienceTarget, iconImg, headerTitle, description, submitText, subscribers, keyColor, primaryColor, url, subredditType, created);
    }

    @Override
    public String toString() {
        return "Subreddit{" +
                "bannerImg='" + bannerImg + '\'' +
                ", displayNamePrefixed='" + displayNamePrefixed + '\'' +
                ", displayName='" + displayName + '\'' +
                ", title='" + title + '\'' +
                ", headerImg='" + headerImg + '\'' +
                ", audienceTarget='" + audienceTarget + '\'' +
                ", iconImg='" + iconImg + '\'' +
                ", headerTitle='" + headerTitle + '\'' +
                ", description='" + description + '\'' +
                ", submitText='" + submitText + '\'' +
                ", subscribers=" + subscribers +
                ", keyColor='" + keyColor + '\'' +
                ", primaryColor='" + primaryColor + '\'' +
                ", url='" + url + '\'' +
                ", subredditType='" + subredditType + '\'' +
                ", created=" + created +
                '}';
    }
}
