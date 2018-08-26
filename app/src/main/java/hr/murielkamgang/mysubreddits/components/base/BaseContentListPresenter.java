package hr.murielkamgang.mysubreddits.components.base;

import android.support.v4.util.Pair;
import android.support.v7.util.DiffUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import hr.murielkamgang.mysubreddits.R;
import hr.murielkamgang.mysubreddits.util.Utils;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by muriel on 3/24/18.
 */
public abstract class BaseContentListPresenter<T, V extends BaseContentListContract.View<T>> implements BaseContentListContract.Presenter<V> {

    private final Logger logger = LoggerFactory.getLogger(BaseContentListPresenter.class);
    protected V view;
    protected List<T> oldItems;
    private Disposable loadDisposable;

    protected abstract Observable<List<T>> provideLoadObservable(boolean sync);

    protected DiffUtil.Callback provideCustomDiffCallback(List<T> oldItems, List<T> newItems) {
        return new DiffCallback(oldItems, newItems);
    }

    @Override
    public void onDestroy() {
        view = null;
        disposeLoad();
    }

    @Override
    public void setView(V v) {
        this.view = v;
        internalLoad(false);
    }

    @Override
    public void load() {
        internalLoad(true);

    }

    protected void internalLoad(boolean sync) {
        internalLoad(provideLoadObservable(sync), sync);
    }

    protected void internalLoad(Observable<List<T>> observable, boolean sync) {
        disposeLoad();
        if (!sync || Utils.isConnected(view)) {
            loadDisposable = observable
                    .doOnSubscribe(disposable -> handleOnSubscribed())
                    .observeOn(Schedulers.io())
                    .map(ts -> {
                        DiffUtil.DiffResult result = DiffUtil.calculateDiff(provideCustomDiffCallback(oldItems, ts));
                        return new Pair<>(ts, result);
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(pair -> handleNewItems(pair, sync), this::handleOnError, this::onComplete);
        } else {
            view.swipeToRefresh(false);
        }
    }

    protected void handleNewItems(Pair<List<T>, DiffUtil.DiffResult> pair, boolean sync) {
        if (view != null) {
            view.onLoaded(pair.first, pair.second);
            oldItems = pair.first;
            if (!sync && (oldItems != null && !oldItems.isEmpty())) {
                view.swipeToRefresh(false);
            }
        }
    }

    protected void handleOnError(Throwable throwable) {
        if (view != null) {
            view.toast(R.string.error_msg_something_went_wrong);
            view.swipeToRefresh(false);
        }
        logger.debug("", throwable);
    }

    protected void onComplete() {
        if (view != null) {
            view.swipeToRefresh(false);
        }
    }

    protected void handleOnSubscribed() {
        if (view != null) {
            view.swipeToRefresh(true);
        }
    }

    private void disposeLoad() {
        if (loadDisposable != null) {
            loadDisposable.dispose();
            loadDisposable = null;
        }
    }

    private class DiffCallback extends DiffUtil.Callback {

        private final List<T> oldList;
        private final List<T> newList;

        public DiffCallback(List<T> oldList, List<T> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList == null ? 0 : oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList == null ? 0 : newList.size();
        }

        @Override
        public boolean areItemsTheSame(int i, int i1) {
            return oldList.get(i).equals(newList.get(i1));
        }

        @Override
        public boolean areContentsTheSame(int i, int i1) {
            return oldList.get(i).equals(newList.get(i1));
        }
    }

}
