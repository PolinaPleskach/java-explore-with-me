package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import ru.practicum.category.dto.CategoryResponseDto;
import ru.practicum.event.entity.enums.EventState;
import ru.practicum.event.entity.Location;
import ru.practicum.user.dto.UserDtoWithoutEmail;

import java.time.LocalDateTime;

@Data
public class EventFullDto {
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private Long id;

    private UserDtoWithoutEmail initiator;

    private CategoryResponseDto category;

    private Integer confirmedRequests;

    private Location location;

    private String title;

    private String annotation;

    private String description;

    private EventState state;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime eventDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime createdOn;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_TIME_FORMAT)
    private LocalDateTime publishedOn;

    private Integer participantLimit;

    private Boolean paid;

    private Boolean requestModeration;

    private Integer views;
}