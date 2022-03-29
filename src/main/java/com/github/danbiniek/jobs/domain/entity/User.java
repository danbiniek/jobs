package com.github.danbiniek.jobs.domain.entity;

import com.github.danbiniek.jobs.api.dto.UserDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@ToString
@Getter
@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String lastName;
    private String login;
    private String password;
    @Setter
    private LocalDateTime accountCreationDate;

    public User update(UserDto updated) {
        return new User(this.getId(), updated.getFirstName(), updated.getLastName(),
                updated.getLogin(), updated.getPassword(), this.getAccountCreationDate());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
