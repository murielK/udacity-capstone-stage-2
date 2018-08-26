package hr.murielkamgang.mysubreddits.data.source.base;

import android.support.annotation.NonNull;

import java.util.List;

import hr.murielkamgang.mysubreddits.data.source.DataSource;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by muriel on 24/2/18.
 */

public abstract class BaseLocalDataSource<T, K> implements
        DataSource<T, K> {

    @Override
    public Observable<List<T>> getAllDataAsObservable(K k) {
        return Observable.fromCallable(() -> getAllData(k));
    }

    @Override
    public Completable saveDataAsObservable(@NonNull final T t) {
        return Completable.fromAction(() -> saveData(t));
    }

    @Override
    public Completable saveDataAsObservable(@NonNull final List<T> t) {
        return Completable.fromAction(() -> saveData(t));
    }

    @Override
    public Observable<T> getDataAsObservable(@NonNull final K k) {
        return Observable.fromCallable(() -> getData(k));
    }

    @Override
    public Observable<List<T>> getAllDataAsObservable() {
        return Observable.fromCallable(this::getAllData);
    }

    @Override
    public Completable deleteAsObservable(@NonNull final T t) {
        return Completable.fromAction(() -> delete(t));
    }

    @Override
    public Completable deleteAllAsObservable() {
        return Completable.fromAction(this::deleteAll);
    }

}
