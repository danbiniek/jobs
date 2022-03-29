package com.github.danbiniek.jobs.domain.converter;

import com.github.danbiniek.jobs.api.dto.UserDto;
import com.github.danbiniek.jobs.domain.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class UserDto2EntityConverter implements Converter<UserDto, User> {

    @Override
    public User convert(UserDto source) {
        return new User(source.getId(), source.getFirstName(), source.getLastName(), source.getPassword(),
                source.getLogin(), source.getAccountCreationDate());
    }
}
