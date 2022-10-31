package com.yurueu.event.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
public class EventSaveDto {
    @Schema(description = "Topic", example = "UEFA Champions League №", required = true)
    private String topic;
    @Schema(description = "Description", example = "Description of event", required = true)
    private String description;
    @Schema(description = "Organizer", example = "organizer №1", required = true)
    private String organizer;
    @Schema(description = "Event date", example = "2022-12-02T23:41:45.741957", required = true)
    private LocalDateTime eventDate;
    @Schema(description = "Place", example = "place191", required = true)
    private String place;
}
