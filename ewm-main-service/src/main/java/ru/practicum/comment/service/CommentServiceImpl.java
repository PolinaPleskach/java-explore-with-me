package ru.practicum.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.comment.dao.CommentRepository;
import ru.practicum.comment.dto.CommentRequestDto;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.comment.entity.Comment;
import ru.practicum.comment.mapper.CommentMapper;
import ru.practicum.event.dao.EventRepository;
import ru.practicum.event.entity.Event;
import ru.practicum.event.entity.enums.EventState;
import ru.practicum.exception.ConflictException;
import ru.practicum.exception.NotFoundException;
import ru.practicum.user.dao.UserRepository;
import ru.practicum.user.entity.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final CommentMapper commentMapper;

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllComments(Long userId, Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не найдено."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события не найдено."));
        List<Comment> comments = commentRepository.findByCreatorAndEvent(user, event, pageRequest);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    public CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long userId, Long eventId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Такого пользователя не найдено."));
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события не найдено."));
        if (event.getState() != EventState.PUBLISHED) {
            throw new ConflictException("");
        }
        Comment newComment = commentMapper.mapToComment(commentRequestDto, user, event);
        return commentMapper.mapToCommentResponseDto(commentRepository.save(newComment));
    }

    @Override
    public CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long userId, Long commentId) {
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Такого пользователя не найдено.");
        }
        Comment oldComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Такого комментария не найдено."));
        if (!oldComment.getCreator().getId().equals(userId)) {
            throw new ConflictException("");
        }
        oldComment.setComment(commentRequestDto.getComment());
        return commentMapper.mapToCommentResponseDto(oldComment);
    }

    @Override
    public void deleteComment(final Long userId, final Long commentId) {
        final List<Comment> comments = commentRepository.findAll();
        final Comment comment = comments.get(Math.toIntExact(commentId));

        if (!comment.getCreator().getId().equals(userId) &&
                !comment.getCreator().getId().equals(comment.getEvent().getInitiator().getId())) {
            throw new ConflictException("Удалить комментарий может только его автор или инициатор события.");
        }

        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> getAllCommentsByEvent(Long eventId, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Такого события не найдено."));
        List<Comment> comments = commentRepository.findByEvent(event, pageRequest);
        if (comments.isEmpty()) {
            return new ArrayList<>();
        }
        return commentMapper.toCommentOutputDtoList(comments);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentResponseDto getCommentById(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NotFoundException("Такого комментария не найдено."));
        return commentMapper.mapToCommentResponseDto(comment);
    }

    @Override
    public void deleteCommentByAdmin(Long eventId) {
        commentRepository.deleteById(eventId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDto> searchComments(Long userId, Long eventId, String comment, Integer from, Integer size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        if (!userRepository.existsById(userId)) {
            throw new NotFoundException("Такого пользователя не найдено");
        }
        if (!eventRepository.existsById(eventId)) {
            throw new NotFoundException("Такого события не найдено");
        }
        if (comment.isBlank()) {
            return Collections.emptyList();
        }
        List<Comment> comments = commentRepository
                .findAllByCreatorIdAndEventIdAndCommentContainingIgnoreCase(userId, eventId, comment, pageRequest);
        return commentMapper.toCommentOutputDtoList(comments);
    }
}