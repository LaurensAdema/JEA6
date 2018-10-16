package ma.ade.kwetter2.database.interfaces;

import java.util.stream.Stream;

public interface IBaseDbManager<T>
{
    T get(long id) ;
    
    Stream<T> getAll();
    
    T create(T entity);

    void update(T entity);

    void delete(long id);
}
