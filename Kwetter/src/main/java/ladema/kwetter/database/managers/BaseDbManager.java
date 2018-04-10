package ladema.kwetter.database.managers;

import ladema.kwetter.database.interfaces.IBaseDbManager;

import javax.persistence.EntityManager;

public abstract class BaseDbManager<T> implements IBaseDbManager<T>
{

    private final Class<T> entityClass;

    public BaseDbManager(Class<T> entityClass)
    {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    @Override
    public T get(long id)
    {
        return getEntityManager().find(entityClass, id);
    }

    @Override
    public void create(T entity)
    {
        getEntityManager().persist(entity);
    }

    @Override
    public void update(T entity)
    {
        getEntityManager().merge(entity);
    }

    @Override
    public void delete(T entity)
    {
        getEntityManager().remove(getEntityManager().merge(entity));
    }
}
