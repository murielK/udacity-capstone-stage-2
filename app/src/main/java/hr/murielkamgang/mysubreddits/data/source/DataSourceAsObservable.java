package hr.murielkamgang.mysubreddits.data.source;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * Created by muriel on 24/2/18.
 */

public interface DataSourceAsObservable<T, ID> {

    Completable saveDataAsObservable(final T t);

    Completable saveDataAsObservable(final List<T> t);

    Observable<T> getDataAsObservable(final ID id);

    Observable<List<T>> getAllDataAsObservable();

    Completable deleteAsObservable(final T t);

    Completable deleteAllAsObservable();

    Observable<List<T>> getAllDataAsObservable(final ID id);

}
