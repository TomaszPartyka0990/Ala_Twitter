package pl.sda.partyka.model.dao;

import pl.sda.partyka.hibernate.util.HibernateUtil;

import javax.persistence.EntityManager;

public abstract class AbstractDAO {
    protected final HibernateUtil hibernateUtil = HibernateUtil.getInstance();
    protected final EntityManager entityManager = HibernateUtil.getInstance().getEntityManager();
}
