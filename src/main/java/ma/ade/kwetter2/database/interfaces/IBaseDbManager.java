package ma.ade.kwetter2.database.interfaces;

import java.util.Collection;

public interface IBaseDbManager<T>
{
    T get(long id) ;
    
    Collection<T> getAll();
    
    T create(T entity);

    void update(T entity);

    void delete(long id);
}
