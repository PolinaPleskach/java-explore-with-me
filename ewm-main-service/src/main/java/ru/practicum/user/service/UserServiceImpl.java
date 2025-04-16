package ru.practicum.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.exception.DuplicatedDataException;
import ru.practicum.user.dao.UserRepository;
import ru.practicum.user.dto.UserRequestDto;
import ru.practicum.user.dto.UserResponseDto;
import ru.practicum.user.entity.User;
import ru.practicum.user.mapper.UserMapper;

import java.util.List;
import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    @Transactional(readOnly = true)
    public List<UserResponseDto> getAllUsers(List<Long> ids, int from, int size) {
        PageRequest pageRequest = PageRequest.of(from / size, size);
        List<User> users;
        if (Objects.isNull(ids) || ids.isEmpty()) {
            users = userRepository.findAll(pageRequest).getContent();
        } else {
            users = userRepository.findByIdIn(ids, pageRequest);
        }
        return userMapper.mapToListDto(users);
    }

    @Override
    public UserResponseDto createUser(UserRequestDto userRequestDto) {
        if (userRepository.findAll().contains(userMapper.mapToUser(userRequestDto))) {
            throw new DuplicatedDataException("Этот пользователь уже существует.");
        }
        User user = userRepository.save(userMapper.mapToUser(userRequestDto));
        return userMapper.mapToUserDto(user);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}