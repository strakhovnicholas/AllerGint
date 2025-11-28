package org.allergit.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.allergit.allergen.*;
import org.allergit.entity.User;
import org.allergit.exception.NotFoundException;
import org.allergit.mapper.UserMapper;
import org.allergit.repository.UserRepository;
import org.allergit.user.dto.AllergenDto;
import org.allergit.user.dto.UserDto;
import org.allergit.user.dto.UserSymptomPreferenceDto;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AllergenRepository allergenRepository;
    private final AllergenMapper allergenMapper;
    private final UserSymptomPreferenceMapper userSymptomPreferenceMapper;

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
        User userEntity = userMapper.toEntity(userDto);

        if (userDto.getAllergens() != null && !userDto.getAllergens().isEmpty()) {
            Set<Allergen> allergens = userDto.getAllergens().stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            list -> {
                                List<Allergen> mapped = allergenMapper.toEntityList(list);
                                mapped.forEach(a -> a.setUser(userEntity));
                                return new HashSet<>(mapped);
                            }
                    ));
            userEntity.setAllergens(allergens);
        }

        if (userDto.getSymptomPreferences() != null && !userDto.getSymptomPreferences().isEmpty()) {
            Set<UserSymptomPreference> prefs = userDto.getSymptomPreferences().stream()
                    .collect(Collectors.collectingAndThen(
                            Collectors.toList(),
                            list -> {
                                List<UserSymptomPreference> mapped = userSymptomPreferenceMapper.toEntityList(list);
                                mapped.forEach(p -> p.setUser(userEntity));
                                return new HashSet<>(mapped);
                            }
                    ));
            userEntity.setSymptomPreferences(prefs);
        }

        User savedUser = userRepository.save(userEntity);
        return userMapper.toDto(savedUser);
    }


    public UserDto update(UUID id, UserDto userDto) {
        log.info("Updating user with id: {}", id);

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User with id " + id + " not found!"));

        if (userDto.getName() != null) user.setName(userDto.getName());
        if (userDto.getAge() != null) user.setAge(userDto.getAge());
        if (userDto.getGender() != null) user.setGender(userDto.getGender());
        if (userDto.getEmail() != null) user.setEmail(userDto.getEmail());
        if (userDto.getPassword() != null) user.setPassword(userDto.getPassword());
        if (userDto.getRole() != null) user.setRole(userDto.getRole());

        if (userDto.getAllergens() != null) {
            user.getAllergens().clear();
            for (AllergenDto alDto : userDto.getAllergens()) {
                Allergen al = allergenMapper.toEntity(alDto);
                al.setUser(user);
                user.getAllergens().add(al);
            }
        }

        if (userDto.getSymptomPreferences() != null) {
            user.getSymptomPreferences().clear();
            for (UserSymptomPreferenceDto spDto : userDto.getSymptomPreferences()) {
                UserSymptomPreference sp = userSymptomPreferenceMapper.toEntity(spDto);
                sp.setUser(user);
                user.getSymptomPreferences().add(sp);
            }
        }

        User updated = userRepository.save(user);
        return userMapper.toDto(updated);
    }

}
