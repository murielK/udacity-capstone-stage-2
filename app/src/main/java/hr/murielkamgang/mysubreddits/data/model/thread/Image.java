package hr.murielkamgang.mysubreddits.data.model.thread;

import java.util.Objects;

public class Image {

    private String id;
    private Source source;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id='" + id + '\'' +
                ", source=" + source +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) &&
                Objects.equals(source, image.source);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, source);
    }
}
