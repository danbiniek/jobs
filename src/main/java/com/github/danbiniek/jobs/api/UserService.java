package com.github.danbiniek.jobs.api;

import com.github.danbiniek.jobs.api.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getAllUsers(int size, int page);

    UserDto addUser(UserDto newUser);

    boolean deleteUser(UserDto newUser);

    UserDto updateUser(UserDto userToUpdate);
}
