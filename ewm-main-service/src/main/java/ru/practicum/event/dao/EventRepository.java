package ru.practicum.event.dao;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.category.model.Category;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventState;
import ru.practicum.user.model.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    boolean existsByCategoryId(Long catId);

    List<Event> findByInitiatorId(Long initiatorId, Pageable pageable);

    List<Event> findByInitiatorInAndStateInAndCategoryInAndEventDateAfterAndEventDateBeforeOrderByIdAsc(
            List<User> initiators, List<EventState> states, List<Category> categories,
            LocalDateTime start, LocalDateTime end, Pageable pageable);

    Optional<Event> findByIdAndState(Long eventId, EventState state);

    List<Event> findByIdIn(List<Long> eventIds);
}