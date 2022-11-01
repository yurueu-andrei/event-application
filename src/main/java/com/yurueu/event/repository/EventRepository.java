package com.yurueu.event.repository;

import com.yurueu.event.dto.UserRequestFilter;
import com.yurueu.event.entity.Event;

import java.util.List;

public interface EventRepository {
    String ID_COLUMN = "id";
    String TOPIC_COLUMN = "topic";
    String DESCRIPTION_COLUMN = "description";
    String ORGANIZER_COLUMN = "organizer";
    String EVENT_DATE_COLUMN = "eventDate";
    String PLACE_COLUMN = "place";
    String IS_DATE_FROM_NULL = "isDateFromNull";
    String DATE_FROM = "dateFrom";
    String IS_DATE_TO_NULL = "isDateToNull";
    String DATE_TO = "dateTo";
    String IS_ORGANIZER_NULL = "isOrganizerNull";
    String IS_TOPIC_NULL = "isTopicNull";
    String LIMIT = "limit";
    String OFFSET = "offset";


    Event findById(Long id);
    List<Event> findAll(UserRequestFilter userRequestFilter);
    Event add(Event event);
    boolean update(Event event);
    boolean delete(Long id);
}
