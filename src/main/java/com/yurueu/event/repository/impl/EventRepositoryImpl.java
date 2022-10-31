package com.yurueu.event.repository.impl;

import com.yurueu.event.entity.Event;
import com.yurueu.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {

    private final SessionFactory sessionFactory;

    private static final String SELECT_ALL_QUERY = """
    SELECT *
    FROM events
    LIMIT :limit
    OFFSET :offset
    """;
    private static final String UPDATE_QUERY = """
                UPDATE Event
                SET topic = :topic, description = :description, organizer = :organizer, eventDate = :eventDate, place = :place
                WHERE id = :id
            """;

    @Override
    public List<Event> findAll(int limit, int offset) {
        try (Session session = sessionFactory.openSession()) {
            return session.createNativeQuery(SELECT_ALL_QUERY, Event.class)
                    .setParameter("limit", limit)
                    .setParameter("offset", offset)
                    .list();
        }
    }

    @Override
    public Event findById(Long id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Event.class, id);
        }
    }

    @Override
    public Event add(Event event) {
        try (Session session = sessionFactory.openSession()) {
            session.save(event);
            return event;
        }
    }

    @Override
    public boolean update(Event event) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                session.createQuery(UPDATE_QUERY)
                        .setParameter(TOPIC_COLUMN, event.getTopic())
                        .setParameter(DESCRIPTION_COLUMN, event.getDescription())
                        .setParameter(ORGANIZER_COLUMN, event.getOrganizer())
                        .setParameter(EVENT_DATE_COLUMN, event.getEventDate())
                        .setParameter(PLACE_COLUMN, event.getPlace())
                        .setParameter(ID_COLUMN, event.getId())
                        .executeUpdate();
                session.getTransaction().commit();
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
            return true;
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            Event event = session.get(Event.class, id);
            session.delete(event);
            return true;
        }
    }
}
