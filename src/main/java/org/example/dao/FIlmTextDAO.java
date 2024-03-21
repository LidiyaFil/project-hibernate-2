package org.example.dao;

import org.example.tables.FIlmText;
import org.hibernate.SessionFactory;

public class FIlmTextDAO extends GenericDAO<FIlmText> {
    public FIlmTextDAO(SessionFactory sessionFactory) {
        super(FIlmText.class, sessionFactory);
    }
}
