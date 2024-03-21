package org.example.dao;

import org.example.tables.Film;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;


public class FilmDAO extends GenericDAO<Film> {
    public FilmDAO(SessionFactory sessionFactory) {
        super(Film.class, sessionFactory);
    }

    public Film getFirstAvailableFilm() {
        Query<Film> query = getCurrentSession().createQuery(
                "from Film where id not in (select distinct film.id from Inventory)", Film.class);
        query.setMaxResults(1);
        return query.getSingleResult();
    }
}
