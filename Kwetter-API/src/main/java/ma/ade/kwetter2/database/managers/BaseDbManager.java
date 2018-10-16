package ma.ade.kwetter2.database.managers;

import ma.ade.kwetter2.database.interfaces.IBaseDbManager;

import javax.persistence.EntityManager;

public abstract class BaseDbManager<T> implements IBaseDbManager<T> {

    private final Class<T> entityClass;

    public BaseDbManager(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    protected abstract EntityManager getEntityManager();

    public T get(long id) {
        return getEntityManager().find(entityClass, id);
    }

    public T create(T entity) {
        getEntityManager().persist(entity);
        return entity;
    }

    public void update(T entity) {
        getEntityManager().merge(entity);
    }

    public void delete(long id) {
        getEntityManager().remove(getEntityManager().find(entityClass, id));
    }
}
