package com.yurueu.event.service.impl;

import com.yurueu.event.dto.EventDto;
import com.yurueu.event.dto.EventSaveDto;
import com.yurueu.event.entity.Event;
import com.yurueu.event.exception.ServiceException;
import com.yurueu.event.mapper.EventMapper;
import com.yurueu.event.repository.EventRepository;
import com.yurueu.event.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public EventDto findById(Long id) {
        try {
            return eventMapper.toDto(eventRepository.findById(id));
        } catch (Exception ex) {
            throw new ServiceException("The events were not found", ex);
        }
    }

    @Override
    public List<EventDto> findAll(int limit, int offset) {
        try {
            return eventMapper.toDto(eventRepository.findAll(limit, offset));
        } catch (Exception ex) {
            throw new ServiceException("The events were not found", ex);
        }
    }

    @Override
    public EventDto add(EventSaveDto eventSaveDto) {
        try {
            Event event = eventMapper.fromSaveDto(eventSaveDto);
            return eventMapper.toDto(eventRepository.add(event));
        } catch (Exception ex) {
            throw new ServiceException(String.format("The event was not saved. %s", eventSaveDto), ex);
        }
    }

    @Override
    public boolean update(EventDto eventDto) {
        try {
            Event event = eventMapper.fromDto(eventDto);
            return eventRepository.update(event);
        } catch (Exception ex) {
            throw new ServiceException(String.format("The event was not saved. %s", eventDto), ex);
        }
    }

    @Override
    public boolean delete(Long id) {
        try {
            return eventRepository.delete(id);
        } catch (Exception ex) {
            throw new ServiceException(String.format("%s: {%s}", getClass().getSimpleName(), ex.getMessage()));
        }
    }
}
