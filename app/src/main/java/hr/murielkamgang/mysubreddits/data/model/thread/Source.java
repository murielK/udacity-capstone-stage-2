package hr.murielkamgang.mysubreddits.data.model.thread;

import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Objects;

public class Source {

    @NonNull
    @PrimaryKey
    private String url;
    private int width;
    private int height;

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Source{" +
                "url='" + url + '\'' +
                ", width=" + width +
                ", height=" + height +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return width == source.width &&
                height == source.height &&
                Objects.equals(url, source.url);
    }

    @Override
    public int hashCode() {

        return Objects.hash(url, width, height);
    }
}

