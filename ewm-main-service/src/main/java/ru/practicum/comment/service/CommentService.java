package ru.practicum.comment.service;

import ru.practicum.comment.dto.CommentRequestDto;
import ru.practicum.comment.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    List<CommentResponseDto> getAllComments(Long userId, Long eventId, int from, int size);

    CommentResponseDto createComment(CommentRequestDto commentRequestDto, Long userId, Long eventId);

    CommentResponseDto updateComment(CommentRequestDto commentRequestDto, Long userId, Long commentId);

    void deleteComment(Long userId, Long commentId);

    List<CommentResponseDto> getAllCommentsByEvent(Long eventId, int from, int size);

    CommentResponseDto getCommentById(Long commentId);

    void deleteCommentByAdmin(Long eventId);

    List<CommentResponseDto> searchComments(Long userId, Long eventId, String comment, Integer from, Integer size);
}