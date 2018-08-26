package hr.murielkamgang.mysubreddits.data.source;

import java.util.List;

/**
 * Created by muriel on 24/2/18.
 */

public interface DataSource<T, ID> extends DataSourceAsObservable<T, ID> {

    T getData(final ID id);

    List<T> getAllData(final ID id);

    List<T> getAllData();

    void saveData(final T t);

    void saveData(final List<T> t);

    void delete(final T t);

    void deleteAll();
}
