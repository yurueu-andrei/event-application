package com.yurueu.event.repository.impl;

import com.yurueu.event.dto.UserRequestFilter;
import com.yurueu.event.entity.Event;
import com.yurueu.event.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@RequiredArgsConstructor
public class EventRepositoryImpl implements EventRepository {
    private static final String SELECT_ALL_QUERY = """
        SELECT *
        FROM events
        WHERE (:isDateFromNull OR event_date >= :dateFrom)
            AND (:isDateToNull OR event_date <= :dateTo)
            AND (:isOrganizerNull OR organizer = :organizer)
            AND (:isTopicNull OR topic = :topic)
        LIMIT :limit
        OFFSET :offset
        """;

    private static final String UPDATE_QUERY = """
        UPDATE Event
        SET topic = :topic, description = :description, organizer = :organizer, eventDate = :eventDate, place = :place
        WHERE id = :id
        """;

    private final SessionFactory sessionFactory;

    @Override
    public List<Event> findAll(UserRequestFilter userRequestFilter) {
        try (Session session = sessionFactory.openSession()) {
            return session.createNativeQuery(SELECT_ALL_QUERY, Event.class)
                    .setParameter(IS_DATE_FROM_NULL, Objects.isNull(userRequestFilter.getDateFrom()))
                    .setParameter(DATE_FROM, userRequestFilter.getDateFrom() == null
                            ? LocalDateTime.now()
                            : userRequestFilter.getDateFrom())
                    .setParameter(IS_DATE_TO_NULL, Objects.isNull(userRequestFilter.getDateTo()))
                    .setParameter(DATE_TO, userRequestFilter.getDateTo() == null
                            ? LocalDateTime.now()
                            : userRequestFilter.getDateTo())
                    .setParameter(IS_ORGANIZER_NULL, Objects.isNull(userRequestFilter.getOrganizer()))
                    .setParameter(ORGANIZER_COLUMN, String.valueOf(userRequestFilter.getOrganizer()))
                    .setParameter(IS_TOPIC_NULL, Objects.isNull(userRequestFilter.getTopic()))
                    .setParameter(TOPIC_COLUMN, String.valueOf(userRequestFilter.getTopic()))

                    .setParameter(LIMIT, userRequestFilter.getLimit())
                    .setParameter(OFFSET, userRequestFilter.getOffset())
                    .getResultList();
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
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
            return false;
        }
    }

    @Override
    public boolean delete(Long id) {
        try (Session session = sessionFactory.openSession()) {
            session.getTransaction().begin();
            try {
                Event event = session.get(Event.class, id);
                session.delete(event);
                session.getTransaction().commit();
                return true;
            } catch (Exception ex) {
                session.getTransaction().rollback();
            }
            return false;
        }
    }
}
