package ma.ade.kwetter2.database.managers;

import ma.ade.kwetter2.database.interfaces.IBaseDbManager;

import javax.persistence.EntityManager;

public abstract class BaseDbManager<T> implements IBaseDbManager<T>
{

    private final Class<T> entityClass;

    public BaseDbManager(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T get(long id)
    {
        return getEntityManager().find(entityClass, id);
    }

    public void create(T entity)
    {
        getEntityManager().persist(entity);
    }

    public void update(T entity)
    {
        getEntityManager().merge(entity);
    }

    public void delete(T entity)
    {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
}
