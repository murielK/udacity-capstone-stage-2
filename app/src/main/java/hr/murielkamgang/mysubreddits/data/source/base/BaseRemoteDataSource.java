package hr.murielkamgang.mysubreddits.data.source.base;

import java.util.List;

import hr.murielkamgang.mysubreddits.data.source.DataSource;
import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by muriel on 24/2/18.
 */

public abstract class BaseRemoteDataSource<T, ID> implements DataSource<T, ID> {

    @Override
    public T getData(ID id) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Completable saveDataAsObservable(T t) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public List<T> getAllData() {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Completable saveDataAsObservable(List<T> t) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void saveData(T t) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void saveData(List<T> t) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Observable<T> getDataAsObservable(ID id) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void delete(T t) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public void deleteAll() {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Observable<List<T>> getAllDataAsObservable() {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Completable deleteAsObservable(T t) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Completable deleteAllAsObservable() {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public List<T> getAllData(ID id) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Observable<List<T>> getAllDataAsObservable(ID id) {
        throw new IllegalStateException("not implemented");
    }
}