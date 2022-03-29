package com.github.danbiniek.jobs.domain.converter;

import com.github.danbiniek.jobs.api.dto.CategoryDto;
import com.github.danbiniek.jobs.api.dto.JobOfferDto;
import com.github.danbiniek.jobs.api.dto.UserDto;
import com.github.danbiniek.jobs.domain.entity.JobOffer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JobOffer2DtoConverter implements Converter<JobOffer, JobOfferDto> {

    @Override
    public JobOfferDto convert(JobOffer source) {
        CategoryDto categoryDto = new CategoryDto(source.getCategory().getId(), source.getCategory().getName());
        UserDto userDto = new UserDto(source.getEmployer().getId(), source.getEmployer().getFirstName(), source.getEmployer().getLastName(),
                source.getEmployer().getLogin(), source.getEmployer().getPassword(), source.getEmployer().getAccountCreationDate());

        return new JobOfferDto(source.getId(), categoryDto, userDto, source.getStartDate(), source.getEndDate());
    }
}
