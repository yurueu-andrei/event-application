package com.yurueu.event.repository;

import com.yurueu.event.entity.Event;

import java.util.List;

public interface EventRepository {
    String ID_COLUMN = "id";
    String TOPIC_COLUMN = "topic";
    String DESCRIPTION_COLUMN = "description";
    String ORGANIZER_COLUMN = "organizer";
    String EVENT_DATE_COLUMN = "eventDate";
    String PLACE_COLUMN = "place";

    Event findById(Long id);
    List<Event> findAll(int limit, int offset);
    Event add(Event event);
    boolean update(Event event);
    boolean delete(Long id);
}
