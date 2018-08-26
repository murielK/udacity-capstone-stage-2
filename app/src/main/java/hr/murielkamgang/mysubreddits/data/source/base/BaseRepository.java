package hr.murielkamgang.mysubreddits.data.source.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import hr.murielkamgang.mysubreddits.data.source.Repository;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by muriel on 24/2/18.
 */

public abstract class BaseRepository<T, K> implements Repository<T, K> {

    private final Logger logger = LoggerFactory.getLogger(BaseRepository.class);

    @Override
    public Completable saveDataAsObservable(T t) {
        throw new IllegalStateException("not implemented");
    }

    @Override
    public Completable saveDataAsObservable(List<T> t) {
        return getLocalDataSource().saveDataAsObservable(t)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<T> getDataAsObservable(K k) {
        return getDataAsObservable(k, false);
    }

    @Override
    public Observable<List<T>> getAllDataAsObservable() {
        return getAllDataAsObservable(false);
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
    public Observable<T> getDataAsObservable(K k, boolean sync) {
        return sync ? getAndSaveAsObservable(k) : getLocalDataSource()
                .getDataAsObservable(k)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    logger.debug("", throwable);
                    return Observable.empty();
                })
                .concatWith(getAndSaveAsObservable(k));
    }

    @Override
    public Observable<List<T>> getAllDataAsObservable(boolean sync) {
        return sync ? getAllAndSaveAsObservable(true) : getLocalDataSource()
                .getAllDataAsObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    logger.debug("", throwable);
                    return Observable.empty();
                })
                .concatWith(getAllAndSaveAsObservable(false));
    }

    @Override
    public abstract BaseLocalDataSource<T, K> getLocalDataSource();

    @Override
    public abstract BaseRemoteDataSource<T, K> getRemoteDataSource();

    private Observable<T> getAndSaveAsObservable(final K k) {
        return getRemoteDataSource().getDataAsObservable(k)
                .subscribeOn(Schedulers.io())
                .doOnNext(t -> getLocalDataSource().saveData(t))
                .flatMap(t -> getLocalDataSource().getDataAsObservable(k))
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected Observable<List<T>> getAllAndSaveAsObservable(final boolean sync) {
        return getRemoteDataSource().getAllDataAsObservable()
                .subscribeOn(Schedulers.io())
                .doOnNext(ts -> handleOnNext(ts, sync))
                .flatMap(newsList -> getLocalDataSource().getAllDataAsObservable())
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected Observable<List<T>> getAllAndSaveAsObservable(K k, final boolean sync) {
        return getRemoteDataSource().getAllDataAsObservable(k)
                .subscribeOn(Schedulers.io())
                .doOnNext(ts -> handleOnNext(ts, sync))
                .flatMap(newsList -> getLocalDataSource().getAllDataAsObservable(k))
                .observeOn(AndroidSchedulers.mainThread());
    }

    protected void handleOnNext(List<T> ts, boolean sync) {
        final BaseLocalDataSource<T, K> localDataSource = getLocalDataSource();
        if (sync) {
            localDataSource.deleteAll();
        }
        localDataSource.saveData(ts);
    }

    @Override
    public Observable<List<T>> getAllDataAsObservable(K k) {
        return getAllDataAsObservable(k, false);
    }

    @Override
    public Observable<List<T>> getAllDataAsObservable(K k, boolean sync) {
        return sync ? getAllAndSaveAsObservable(k, true) : getLocalDataSource()
                .getAllDataAsObservable(k)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(throwable -> {
                    logger.debug("", throwable);
                    return Observable.empty();
                })
                .concatWith(getAllAndSaveAsObservable(k, false));
    }
}
