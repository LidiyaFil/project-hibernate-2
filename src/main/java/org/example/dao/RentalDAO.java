package org.example.dao;

import org.example.tables.Rental;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class RentalDAO extends GenericDAO<Rental> {
    public RentalDAO(SessionFactory sessionFactory) {
        super(Rental.class, sessionFactory);
    }

    public Rental getAnyItem() {
        Query<Rental> query = getCurrentSession().createQuery("from Rental where returnDate is null", Rental.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
