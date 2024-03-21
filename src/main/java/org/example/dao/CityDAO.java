package org.example.dao;

import org.example.tables.City;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class CityDAO extends GenericDAO<City> {
    public CityDAO(SessionFactory sessionFactory) {
        super(City.class, sessionFactory);
    }

    public City getByName(String name) {
        Query<City> query = getCurrentSession().createQuery("from City where city = :NAME", City.class);
        query.setParameter("NAME", name);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
