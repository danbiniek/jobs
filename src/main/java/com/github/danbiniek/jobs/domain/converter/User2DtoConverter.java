package com.github.danbiniek.jobs.domain.converter;

import com.github.danbiniek.jobs.api.dto.UserDto;
import com.github.danbiniek.jobs.domain.entity.User;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class User2DtoConverter implements Converter<User, UserDto> {

    @Override
    public UserDto convert(User source) {
        return new UserDto(source.getId(), source.getFirstName(), source.getLastName(),
                source.getLogin(), source.getPassword(), source.getAccountCreationDate());
    }
}
