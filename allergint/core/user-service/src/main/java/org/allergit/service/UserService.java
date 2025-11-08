package org.allergit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergit.entity.User;
import org.allergit.exception.NotFoundException;
import org.allergit.mapper.UserMapper;
import org.allergit.repository.UserRepository;
import org.allergit.user.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getById(UUID id) {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
        return userMapper.toDto(user);
    }

    public List<UserDto> getAll() {
        log.info("Fetching all users");
        return userRepository.findAll().stream().map(userMapper::toDto).toList();
    }

    public UserDto create(UserDto userDto) {
        log.info("Creating new user: {}", userDto.getName());
        User saved = userRepository.save(userMapper.toEntity(userDto));
        return userMapper.toDto(saved);
    }

    public UserDto update(UUID id, UserDto userDto) {
        log.info("Updating user with id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
        userMapper.partialUpdate(userDto, user);
        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

    public void delete(UUID id) {
        log.info("Deleting user with id: {}", id);
        if (!userRepository.existsById(id)) {
            throw new NotFoundException("User with id " + id + " not found!");
        }
        userRepository.deleteById(id);
    }
}
