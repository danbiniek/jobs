package com.github.danbiniek.jobs.domain;

import com.github.danbiniek.jobs.api.UserService;
import com.github.danbiniek.jobs.api.dto.UserDto;
import com.github.danbiniek.jobs.domain.converter.User2DtoConverter;
import com.github.danbiniek.jobs.domain.converter.UserDto2EntityConverter;
import com.github.danbiniek.jobs.domain.entity.User;
import com.github.danbiniek.jobs.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultUserService implements UserService {

    private final Clock clock;
    private final UserRepository userRepository;
    private final User2DtoConverter toDtoConverter;
    private final UserDto2EntityConverter toEntityConverter;

    @Override
    public List<UserDto> getAllUsers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return userRepository.findAll(pageRequest)
                .getContent()
                .stream()
                .map(toDtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto addUser(UserDto newUser) {
        User userToSave = toEntityConverter.convert(newUser);
        userToSave.setAccountCreationDate(LocalDateTime.now(clock));
        User saved = userRepository.save(userToSave);
        return toDtoConverter.convert(saved);
    }

    @Override
    public boolean deleteUser(UserDto newUser) {
        try {
            userRepository.deleteById(newUser.getId());
        } catch (IllegalArgumentException ex) {
            return false;
        }
        return true;
    }

    @Override
    public UserDto updateUser(UserDto userToUpdate) {
        User updated = update(userToUpdate);
        User saved = userRepository.save(updated);
        return toDtoConverter.convert(saved);
    }

    private User update(UserDto userToUpdate) {
        return userRepository.findById(userToUpdate.getId())
                .map(e -> e.update(userToUpdate))
                .orElseThrow(() -> new IllegalArgumentException("User does not exist"));
    }
}