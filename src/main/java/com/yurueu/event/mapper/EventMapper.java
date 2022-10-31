package com.yurueu.event.mapper;

import com.yurueu.event.dto.EventDto;
import com.yurueu.event.dto.EventSaveDto;
import com.yurueu.event.entity.Event;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventMapper {
    EventDto toDto(Event event);
    Event fromDto(EventDto eventDto);
    Event fromSaveDto(EventSaveDto eventDto);
    List<EventDto> toDto(List<Event> events);
}