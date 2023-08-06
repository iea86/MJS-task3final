package com.epam.esm.dao.impl;

import com.epam.esm.dao.OrderDAO;
import com.epam.esm.entity.*;
import com.epam.esm.entity.Order;
import com.epam.esm.exception.DAOException;
import com.epam.esm.utils.StringParser;
import org.hibernate.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO {

    private static final String USER = "user";
    private static final String ID = "id";
    private static final int DEFAULT_PAGE_SIZE = 100;
    private static final String ORDER_DESC = "desc";

    private SessionFactory sessionFactory;

    @Autowired
    public OrderDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Long create(Order entity) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (Long) session.save(entity);
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Order get(Long orderId) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return session.get(Order.class, orderId);
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Order> getByUser(Long userId, Pagination pagination) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
            Root<Order> p = criteriaQuery.from(Order.class);
            Predicate condition = criteriaBuilder.equal(p.get(USER).get(ID), userId);
            criteriaQuery.where(condition);
            String orderBy = StringParser.getOrderBy(pagination.getOrder());
            String orderType = StringParser.getOrderType(pagination.getOrder());
            if (orderType.equals(ORDER_DESC)) {
                criteriaQuery.orderBy(criteriaBuilder.desc(p.get(orderBy)));
            } else {
                criteriaQuery.orderBy(criteriaBuilder.asc(p.get(orderBy)));
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
    public List<Order> getAllOrders() throws DAOException {
        try {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
        CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        Root<Order> rootEntry = criteriaQuery.from(Order.class);
        CriteriaQuery<Order> all = criteriaQuery.select(rootEntry);
        TypedQuery<Order> allQuery = session.createQuery(all);
        return allQuery.getResultList();
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public void update(Order entity) {
        throw new UnsupportedOperationException("operation is not supported");
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
