package ru.practicum.comment.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.comment.entity.Comment;
import ru.practicum.event.entity.Event;
import ru.practicum.user.entity.User;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEvent(Event event, PageRequest pageRequest);

    List<Comment> findByCreatorAndEvent(User creator, Event event, PageRequest pageRequest);

    List<Comment> findAllByCreatorIdAndEventIdAndCommentContainingIgnoreCase(Long creatorId,
                                                                             Long eventId,
                                                                             String comment,
                                                                             PageRequest request);
}