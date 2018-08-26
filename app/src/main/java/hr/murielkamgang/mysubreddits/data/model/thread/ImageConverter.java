package hr.murielkamgang.mysubreddits.data.model.thread;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ImageConverter {

    @TypeConverter
    public String fromImages(List<Image> images) {
        if (images == null) {
            return (null);
        }
        final Gson gson = new Gson();
        final Type type = new TypeToken<List<Image>>() {
        }.getType();
        return gson.toJson(images, type);
    }

    @TypeConverter
    public List<Image> toImages(String images) {
        if (images == null) {
            return (null);
        }
        final Gson gson = new Gson();
        final Type type = new TypeToken<List<Image>>() {
        }.getType();
        return gson.fromJson(images, type);
    }
}
