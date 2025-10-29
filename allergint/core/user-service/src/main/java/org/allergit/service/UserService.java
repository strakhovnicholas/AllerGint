package org.allergit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergit.entity.User;
import org.allergit.exception.NotFoundException;
import org.allergit.mapper.UserMapper;
import org.allergit.repository.UserRepository;
import org.allergit.user.dto.UserDto;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto getUserById(UUID id) {
        log.info("Fetching user by id: {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));
        log.info("Fetched user with id: {}", id);
        return userMapper.toDto(user);
    }
}
