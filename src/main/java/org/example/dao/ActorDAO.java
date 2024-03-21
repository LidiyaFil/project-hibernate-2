package org.example.dao;

import org.example.tables.Actor;
import org.hibernate.SessionFactory;

public class ActorDAO extends GenericDAO<Actor> {
    public ActorDAO(SessionFactory sessionFactory) {
        super(Actor.class, sessionFactory);
    }
}
