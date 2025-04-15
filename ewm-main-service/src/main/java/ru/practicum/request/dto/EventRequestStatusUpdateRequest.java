package ru.practicum.request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import ru.practicum.request.entity.enums.ParticipationRequestStatus;

import java.util.List;

@Data
public class EventRequestStatusUpdateRequest {
    @NotNull
    private List<Long> requestIds;

    @NotNull
    private ParticipationRequestStatus status;
}