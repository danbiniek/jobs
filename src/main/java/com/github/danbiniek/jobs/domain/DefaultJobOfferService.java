package com.github.danbiniek.jobs.domain;

import com.github.danbiniek.jobs.api.JobOfferService;
import com.github.danbiniek.jobs.api.dto.JobOfferDto;
import com.github.danbiniek.jobs.api.exception.ItemDoesNotExistException;
import com.github.danbiniek.jobs.domain.converter.JobOffer2DtoConverter;
import com.github.danbiniek.jobs.domain.converter.JobOfferDto2EntityConverter;
import com.github.danbiniek.jobs.domain.entity.Category;
import com.github.danbiniek.jobs.domain.entity.JobOffer;
import com.github.danbiniek.jobs.domain.entity.User;
import com.github.danbiniek.jobs.domain.repository.CategoryRepository;
import com.github.danbiniek.jobs.domain.repository.JobOfferRepository;
import com.github.danbiniek.jobs.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DefaultJobOfferService implements JobOfferService {

    private final JobOfferDto2EntityConverter dto2EntityConverter;
    private final JobOffer2DtoConverter entity2DtoConverter;
    private final JobOfferRepository jobOfferRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;

    @Override
    public List<JobOfferDto> getAllJobOffers(int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return jobOfferRepository.findAll(pageRequest)
                .getContent()
                .stream().map(entity2DtoConverter::convert)
                .collect(Collectors.toList());
    }

    @Override
    public JobOfferDto addJobOffer(JobOfferDto newJobOffer) {
        validateJobOffer(newJobOffer);
        JobOffer jobOfferToSave = dto2EntityConverter.convert(newJobOffer);
        updateIds(jobOfferToSave);
        JobOffer saved = jobOfferRepository.save(jobOfferToSave);
        return entity2DtoConverter.convert(saved);
    }

    private void validateJobOffer(JobOfferDto newJobOffer) {
        if (newJobOffer.getStartDate().isAfter(newJobOffer.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot pass end date");
        }
    }

    private void updateIds(JobOffer jobOfferToSave) {
        Category existingCategory = validateCategory(jobOfferToSave);
        User existingUser = validateUser(jobOfferToSave);
        jobOfferToSave.setCategory(existingCategory);
        jobOfferToSave.setEmployer(existingUser);
    }

    private Category validateCategory(JobOffer jobOfferToSave) {
        Integer categoryId = jobOfferToSave.getCategory().getId();
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ItemDoesNotExistException("Category with id " + categoryId + " does not exist"));
    }

    private User validateUser(JobOffer jobOfferToSave) {
        Integer userId = jobOfferToSave.getEmployer().getId();
        return userRepository.findById(userId)
                .orElseThrow(() -> new ItemDoesNotExistException("User with id " + userId + " does not exist"));
    }
}
