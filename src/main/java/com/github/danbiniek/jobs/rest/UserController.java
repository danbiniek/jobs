package com.github.danbiniek.jobs.rest;

import com.github.danbiniek.jobs.api.UserService;
import com.github.danbiniek.jobs.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> listPagedUsers(@RequestParam(defaultValue = "100") int size, @RequestParam(defaultValue = "0") int page) {
        return userService.getAllUsers(page, size);
    }

    @PostMapping
    public UserDto addUser(@RequestBody UserDto newUser) {
        return userService.addUser(newUser);
    }

    @PutMapping
    public UserDto updateUser(@RequestBody UserDto userToUpdate) {
        return userService.updateUser(userToUpdate);
    }

    @DeleteMapping
    public boolean deleteUser(@RequestBody UserDto newUser) {
        return userService.deleteUser(newUser);
    }

}
