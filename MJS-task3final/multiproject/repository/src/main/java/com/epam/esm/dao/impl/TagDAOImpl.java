package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDAO;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.StatsEntity;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.utils.StringParser;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Repository
public class TagDAOImpl implements TagDAO {

    private static final int DEFAULT_PAGE_SIZE = 100;
    private static final String ORDER_DESC = "desc";

    private SessionFactory sessionFactory;

    @Autowired
    public TagDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Tag> getAllTags(Pagination pagination) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Tag> criteriaQuery = criteriaBuilder.createQuery(Tag.class);
            Root<Tag> rootEntry = criteriaQuery.from(Tag.class);
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
    public Long create(Tag tag) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            return (Long) session.save(tag);
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Tag get(Long id) {
        return sessionFactory.getCurrentSession().get(Tag.class, id);
    }

    @Override
    public void update(Tag entity) {
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Tag tag = session.load(Tag.class, id);
            session.remove(tag);
            return true;
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }


    @Override
    public Tag find(String name) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(Tag.class);
            Tag tag = (Tag) criteria.add(Restrictions.eq("name", name))
                    .uniqueResult();
            return tag;
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public StatsEntity getTagStats() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            SQLQuery query =
                    session.createSQLQuery("SELECT t.tag_name as attribute, count(t.tag_id) as value\n" +
                            "FROM gift.orders o\n" +
                            "INNER JOIN (\n" +
                            "\t\tSELECT \n" +
                            "\t\to.user_account_id\n" +
                            "\t\tFROM gift.order_details od \n" +
                            "\t\tINNER JOIN gift.orders o\n" +
                            "\t\tON od.order_id = o.order_id\n" +
                            "\n" +
                            "\t\tINNER JOIN gift.gift_certificate gc \n" +
                            "\t\tON od.certificate_id = gc.certificate_id \n" +
                            "\t\tGROUP BY user_account_id\n" +
                            "\t\tORDER BY SUM(gc.price*od.quantity) DESC\n" +
                            "\t\tLIMIT 1\n" +
                            "        ) u\n" +
                            "ON u.user_account_id = o.user_account_id\n" +
                            "\n" +
                            "INNER JOIN  gift.order_details od\n" +
                            "ON o.order_id = od.order_id\n" +
                            "INNER JOIN gift.gift_certificate gc \n" +
                            "ON od.certificate_id = gc.certificate_id\n" +
                            "INNER JOIN gift.certificate_tag ct\n" +
                            "ON gc.certificate_id = ct.certificate_id\n" +
                            "\n" +
                            "INNER JOIN gift.tag t\n" +
                            "ON t.tag_id = ct.tag_id\n" +
                            "GROUP BY t.tag_name\n" +
                            "ORDER BY count(t.tag_id) DESC\n" +
                            "LIMIT 1");
            List<Object[]> rows = query.list();
            StatsEntity stats = new StatsEntity();
            for (Object[] row : rows) {
                stats.setAttribute(row[0].toString());
                stats.setValue(row[1].toString());
            }
            return stats;
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Tag> getAllTags() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
            Root<Tag> rootEntry = cq.from(Tag.class);
            CriteriaQuery<Tag> all = cq.select(rootEntry);
            TypedQuery<Tag> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }
}