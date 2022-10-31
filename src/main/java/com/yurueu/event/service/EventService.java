package com.yurueu.event.service;

import com.yurueu.event.dto.EventDto;
import com.yurueu.event.dto.EventSaveDto;

import java.util.List;

public interface EventService {
    EventDto findById(Long id);
    List<EventDto> findAll(int limit, int offset);
    EventDto add(EventSaveDto eventDto);
    boolean update(EventDto eventDto);
    boolean delete(Long id);
}
