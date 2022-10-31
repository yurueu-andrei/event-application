cpackage com.yurueu.event.controller;

import com.yurueu.event.dto.EventDto;
import com.yurueu.event.dto.EventSaveDto;
import com.yurueu.event.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Tag(name = "Event Controller")
@RequestMapping(value = "/events")
public class EventController {
    private final EventService eventService;

    @GetMapping("/{id}")
    @Operation(summary = "Find event by id")
    public EventDto findById(
        @Schema(description = "Id", example = "1", required = true)
        @PathVariable Long id
    ) {
        return eventService.findById(id);
    }

    @GetMapping
    @Operation(summary = "Find all events")
    public List<EventDto> findAll(
            @Schema(description = "Limit", example = "20", defaultValue = "20")
            @RequestParam(required = false, defaultValue = "20") int limit,
            @Schema(description = "Offset", example = "0", defaultValue = "0")
            @RequestParam(required = false, defaultValue = "0") int offset
    ) {
        return eventService.findAll(limit, offset);
    }

    @PostMapping
    @Operation(summary = "Create new event")
    public EventDto add(@RequestBody EventSaveDto eventSaveDto) {
        return eventService.add(eventSaveDto);
    }

    @PutMapping
    @Operation(summary = "Update existing event")
    public EventDto update(@RequestBody EventDto eventDto) {
        eventService.update(eventDto);
        return eventDto;
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete event")
    public boolean delete(
        @Schema(description = "Id", example = "1", required = true)
        @PathVariable Long id
    ) {
        return eventService.delete(id);
    }
}
