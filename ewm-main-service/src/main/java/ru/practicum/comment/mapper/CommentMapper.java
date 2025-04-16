package ru.practicum.comment.mapper;

import org.springframework.stereotype.Component;
import ru.practicum.comment.dto.CommentRequestDto;
import ru.practicum.comment.dto.CommentResponseDto;
import ru.practicum.comment.entity.Comment;
import ru.practicum.event.entity.Event;
import ru.practicum.event.mapper.EventMapper;
import ru.practicum.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    public Comment mapToComment(CommentRequestDto commentRequestDto, User user, Event event) {
        return new Comment(
                null,
                commentRequestDto.getComment(),
                event,
                user,
                LocalDateTime.now()
        );
    }

    public CommentResponseDto mapToCommentResponseDto(Comment comment) {
        EventMapper eventMapper = new EventMapper();
        return new CommentResponseDto(
                comment.getId(),
                comment.getComment(),
                comment.getCreator().getName(),
                eventMapper.mapToEventShortDto(comment.getEvent()),
                comment.getCreated()
        );
    }

    public List<CommentResponseDto> toCommentOutputDtoList(List<Comment> comments) {
        return comments.stream()
                .map(this::mapToCommentResponseDto)
                .collect(Collectors.toList());
    }
}