package com.github.danbiniek.jobs.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Getter
@AllArgsConstructor
public class UserDto {

    private Integer id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    private LocalDateTime accountCreationDate;

    @JsonCreator
    public UserDto(String firstName, String lastName, String login, String password) {
        Objects.requireNonNull(firstName);
        Objects.requireNonNull(lastName);
        Objects.requireNonNull(login);
        Objects.requireNonNull(password);
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.password = password;
    }
}