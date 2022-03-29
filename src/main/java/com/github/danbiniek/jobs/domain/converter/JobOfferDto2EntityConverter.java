package com.github.danbiniek.jobs.domain.converter;

import com.github.danbiniek.jobs.api.dto.JobOfferDto;
import com.github.danbiniek.jobs.domain.entity.Category;
import com.github.danbiniek.jobs.domain.entity.JobOffer;
import com.github.danbiniek.jobs.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class JobOfferDto2EntityConverter implements Converter<JobOfferDto, JobOffer> {

    @Override
    public JobOffer convert(JobOfferDto source) {
        Category category = new Category(source.getCategory().getId(), source.getCategory().getName());
        User user = new User(source.getEmployer().getId(), source.getEmployer().getFirstName(), source.getEmployer().getLastName(),
                source.getEmployer().getLogin(), source.getEmployer().getPassword(), source.getEmployer().getAccountCreationDate());

        return new JobOffer(source.getId(), category, user, source.getStartDate(), source.getEndDate());
    }
}
