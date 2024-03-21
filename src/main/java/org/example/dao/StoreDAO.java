package org.example.dao;

import org.example.tables.Store;
import org.hibernate.SessionFactory;

public class StoreDAO extends GenericDAO<Store> {
    public StoreDAO(SessionFactory sessionFactory) {
        super(Store.class, sessionFactory);
    }
}
