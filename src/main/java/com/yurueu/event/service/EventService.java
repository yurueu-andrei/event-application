package com.yurueu.event.service;

import com.yurueu.event.dto.EventDto;
import com.yurueu.event.dto.EventSaveDto;
import com.yurueu.event.dto.UserRequestFilter;

import java.util.List;

public interface EventService {
    EventDto findById(Long id);
    List<EventDto> findAll(UserRequestFilter userRequestFilter);
    EventDto add(EventSaveDto eventDto);
    boolean update(EventDto eventDto);
    boolean delete(Long id);
}
