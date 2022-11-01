package com.yurueu.event.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

@Data
@Accessors(chain = true)
@RequiredArgsConstructor
public class UserRequestFilter {
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
    private String organizer;
    private String topic;
    private int limit;
    private int offset;
}
