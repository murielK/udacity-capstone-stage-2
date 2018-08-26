package hr.murielkamgang.mysubreddits.data.model.thread;

import android.arch.persistence.room.TypeConverters;

import java.util.List;
import java.util.Objects;

public class Preview {

    private boolean enable;
    @TypeConverters(ImageConverter.class)
    private List<Image> images;

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Preview preview = (Preview) o;
        return enable == preview.enable &&
                Objects.equals(images, preview.images);
    }

    @Override
    public int hashCode() {

        return Objects.hash(enable, images);
    }

    @Override
    public String toString() {
        return "Preview{" +
                "enable=" + enable +
                ", images=" + images +
                '}';
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }
}

