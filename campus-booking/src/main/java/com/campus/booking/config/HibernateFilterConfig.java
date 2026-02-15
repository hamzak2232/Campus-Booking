package com.campus.booking.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class HibernateFilterConfig {

    @PersistenceContext
    private EntityManager entityManager;

    @PostConstruct
    @Transactional
    public void enableFilter() {
        Session session = entityManager.unwrap(Session.class);
        session.enableFilter("deletedFilter")
                .setParameter("isDeleted", false);
    }
}
