package com.campus.booking.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

@Component
public class HibernateFilterConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void enableFilter() {
//        Session session = entityManager.unwrap(Session.class);
//        session.enableFilter("deletedFilter")
//                .setParameter("isDeleted", false);
    }
}
