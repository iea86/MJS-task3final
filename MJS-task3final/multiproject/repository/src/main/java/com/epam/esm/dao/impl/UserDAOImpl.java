package com.epam.esm.dao.impl;

import com.epam.esm.dao.UserDAO;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.User;
import com.epam.esm.exception.DAOException;
import com.epam.esm.utils.StringParser;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private static final int DEFAULT_PAGE_SIZE = 100;
    private static final String ORDER_DESC = "desc";

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<User> getUsers(Pagination pagination) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> rootEntry = criteriaQuery.from(User.class);
            String orderBy = StringParser.getOrderBy(pagination.getOrder());
            String orderType = StringParser.getOrderType(pagination.getOrder());
            if (orderType.equals(ORDER_DESC)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(rootEntry.get(orderBy)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(rootEntry.get(orderBy)));
            }
            int limit = pagination.getSize() == null ? DEFAULT_PAGE_SIZE : pagination.getSize();
            int offset = pagination.getPage() == null ? 0 : (pagination.getPage() - 1) * limit;
            return session.createQuery(criteriaQuery)
                    .setFirstResult(offset)
                    .setMaxResults(limit)
                    .getResultList();
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<User> criteriaQuery = criteriaBuilder.createQuery(User.class);
            Root<User> rootEntry = criteriaQuery.from(User.class);
            CriteriaQuery<User> all = criteriaQuery.select(rootEntry);
            TypedQuery<User> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Long create(User user) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (Long) session.save(user);
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public User get(Long id) throws DAOException {
        try {
            return sessionFactory.getCurrentSession().get(User.class, id);
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(User entity) {
        throw new UnsupportedOperationException("operation is not supported");
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

}
