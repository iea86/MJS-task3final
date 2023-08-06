package com.epam.esm.dao.impl;

import com.epam.esm.dao.CertificateDAO;
import com.epam.esm.entity.Certificate;
import com.epam.esm.entity.Pagination;
import com.epam.esm.entity.SearchCriteria;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DAOException;
import com.epam.esm.utils.CountInString;
import com.epam.esm.utils.StringParser;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Repository
public class CertificateDAOImpl implements CertificateDAO {

    private static final int DEFAULT_PAGE_SIZE = 10;
    private static final double DEFAULT_PRICE = -1.0;
    private static final Duration DEFAULT_DURATION = Duration.ofDays(-1);

    private SessionFactory sessionFactory;

    @Autowired
    public CertificateDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Certificate get(Long id) {
        return sessionFactory.getCurrentSession().get(Certificate.class, id);
    }

    @Override
    public void update(Certificate entity) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Certificate certificate = session.get(Certificate.class, entity.getId());
            if (entity.getDuration() == DEFAULT_DURATION) {
                entity.setDuration(certificate.getDuration());
            }
            if (entity.getPrice() == DEFAULT_PRICE) {
                entity.setPrice(certificate.getPrice());
            }
            if (entity.getDescription() == null) {
                entity.setDescription(certificate.getDescription());
            }
            if (entity.getName() == null) {
                entity.setName(certificate.getName());
            }
            if (entity.getTags().isEmpty()) {
                entity.setTags(certificate.getTags());
            }
            entity.setCreateDate(certificate.getCreateDate());
            List<Tag> tags = entity.getTags();
            mergeTags(tags, session);
            session.merge(entity);
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Long create(Certificate entity) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            List<Tag> tags = entity.getTags();
            mergeTags(tags, session);
            return (Long) session.save(entity);
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public boolean delete(Long id) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Certificate certificate = session.load(Certificate.class, id);
            session.remove(certificate);
            return true;
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public List<Certificate> getCertificates(SearchCriteria searchCriteria, Pagination pagination) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            String name = searchCriteria.getName();
            String description = searchCriteria.getDescription();
            String orderBy = StringParser.getOrderBy(pagination.getOrder());
            String orderType = StringParser.getOrderType(pagination.getOrder());
            int limit = pagination.getSize() == null ? DEFAULT_PAGE_SIZE : pagination.getSize();
            int offset = pagination.getPage() == null ? 0 : (pagination.getPage() - 1) * limit;
            String tagsNameList = searchCriteria.getTag();
            int count = CountInString.getCountOfElInStr(searchCriteria.getTag());
            List<Certificate> result = (List<Certificate>) session
                    .createNativeQuery("{CALL getCertificatesByCriteria(:name,:description,:orderby,:ordertype,:limit,:offset,:tags,:count)}")
                    .setParameter("name", name)
                    .setParameter("description", description)
                    .setParameter("orderby", orderBy)
                    .setParameter("ordertype", orderType)
                    .setParameter("limit", limit)
                    .setParameter("offset", offset)
                    .setParameter("tags", tagsNameList)
                    .setParameter("count", count)
                    .addEntity(Certificate.class)
                    .list();
            return result;
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    @Override
    public Certificate findCertificate(String name) throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            Criteria criteria = session.createCriteria(Certificate.class);
            return (Certificate) criteria.add(Restrictions.eq("name", name))
                    .uniqueResult();
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    private List<Tag> getAllTags(Session session) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<Tag> cq = cb.createQuery(Tag.class);
        Root<Tag> rootEntry = cq.from(Tag.class);
        CriteriaQuery<Tag> all = cq.select(rootEntry);
        TypedQuery<Tag> allQuery = session.createQuery(all);
        return allQuery.getResultList();
    }

    @Override
    public List<Certificate> getAllCertificates() throws DAOException {
        try {
            Session session = sessionFactory.getCurrentSession();
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Certificate> cq = cb.createQuery(Certificate.class);
            Root<Certificate> rootEntry = cq.from(Certificate.class);
            CriteriaQuery<Certificate> all = cq.select(rootEntry);
            TypedQuery<Certificate> allQuery = session.createQuery(all);
            return allQuery.getResultList();
        } catch (HibernateException e) {
            throw new DAOException(e);
        }
    }

    private void mergeTags(List<Tag> tags, Session session) {
        if (!tags.isEmpty()) {
            List<Tag> tagsExisting = getAllTags(session);
            tags.forEach(t -> {
                Optional<Tag> result = tagsExisting
                        .stream().parallel()
                        .filter(num -> num.getName().equals((t.getName())))
                        .findAny();
                result.ifPresentOrElse(r -> t.setId(r.getId()), () -> session.save(t));
            });
        }
    }
}

