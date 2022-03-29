package com.github.danbiniek.jobs.domain;

import com.github.danbiniek.jobs.api.dto.CategoryDto;
import com.github.danbiniek.jobs.api.dto.JobOfferDto;
import com.github.danbiniek.jobs.api.dto.UserDto;
import com.github.danbiniek.jobs.api.exception.ItemDoesNotExistException;
import com.github.danbiniek.jobs.domain.converter.JobOffer2DtoConverter;
import com.github.danbiniek.jobs.domain.converter.JobOfferDto2EntityConverter;
import com.github.danbiniek.jobs.domain.entity.Category;
import com.github.danbiniek.jobs.domain.entity.JobOffer;
import com.github.danbiniek.jobs.domain.entity.User;
import com.github.danbiniek.jobs.domain.repository.CategoryRepository;
import com.github.danbiniek.jobs.domain.repository.JobOfferRepository;
import com.github.danbiniek.jobs.domain.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultJobOfferServiceTest {

    @Mock
    private JobOfferDto2EntityConverter dto2EntityConverter;
    @Mock
    private JobOffer2DtoConverter entity2DtoConverter;
    @Mock
    private JobOfferRepository jobOfferRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private DefaultJobOfferService jobOfferService;

    @Test
    void aJobOfferPagedList_shouldBeReturned_whenSizeAndPageIsGiven() {
        when(jobOfferRepository.findAll(any(PageRequest.class))).thenReturn(Page.empty());
        jobOfferService.getAllJobOffers(12, 3);

        verify(jobOfferRepository).findAll(PageRequest.of(12, 3));
    }

    @Test
    void aNewJob_shouldBeAdded() {
        User expectedUser = new User(1, "firstName", "lastName", "login", "password", LocalDateTime.now());
        Category expectedCategory = new Category(1, "category");
        when(dto2EntityConverter.convert(any(JobOfferDto.class))).thenCallRealMethod();
        when(categoryRepository.findById(1)).thenReturn(Optional.of(expectedCategory));
        when(userRepository.findById(1)).thenReturn(Optional.of(expectedUser));

        UserDto existingUser = new UserDto(1, "firstName", "lastName", "login", "password", LocalDateTime.now());
        CategoryDto existingCategory = new CategoryDto(1, "category");
        LocalDate start = LocalDate.of(2020, 01, 01);
        LocalDate end = LocalDate.of(2021, 01, 01);
        JobOfferDto newJobOffer = new JobOfferDto(existingCategory, existingUser, start, end);

        jobOfferService.addJobOffer(newJobOffer);

        JobOffer expected = new JobOffer(null, expectedCategory, expectedUser, start, start);
        verify(jobOfferRepository).save(expected);
    }

    @Test
    void aCustomException_shouldBeThrown_whenCategoryNotExist() {
        when(dto2EntityConverter.convert(any(JobOfferDto.class))).thenCallRealMethod();
        when(categoryRepository.findById(1)).thenReturn(Optional.empty());

        UserDto existingUser = new UserDto(1, "firstName", "lastName", "login", "password", LocalDateTime.now());
        CategoryDto existingCategory = new CategoryDto(1, "category");
        LocalDate start = LocalDate.of(2020, 01, 01);
        LocalDate end = LocalDate.of(2021, 01, 01);
        JobOfferDto newJobOffer = new JobOfferDto(existingCategory, existingUser, start, end);

        assertThrows(ItemDoesNotExistException.class, () -> jobOfferService.addJobOffer(newJobOffer));
    }

    @Test
    void aCustomException_shouldBeThrown_whenUserNotExist() {
        Category expectedCategory = new Category(1, "category");
        when(categoryRepository.findById(1)).thenReturn(Optional.of(expectedCategory));
        when(dto2EntityConverter.convert(any(JobOfferDto.class))).thenCallRealMethod();
        when(userRepository.findById(1)).thenReturn(Optional.empty());

        UserDto existingUser = new UserDto(1, "firstName", "lastName", "login", "password", LocalDateTime.now());
        CategoryDto existingCategory = new CategoryDto(1, "category");
        LocalDate start = LocalDate.of(2020, 01, 01);
        LocalDate end = LocalDate.of(2021, 01, 01);
        JobOfferDto newJobOffer = new JobOfferDto(existingCategory, existingUser, start, end);

        assertThrows(ItemDoesNotExistException.class, () -> jobOfferService.addJobOffer(newJobOffer));
    }

    @Test
    void anException_shouldBeThrown_whenJobStartIsAfterJobEnd() {
        UserDto existingUser = new UserDto(1, "firstName", "lastName", "login", "password", LocalDateTime.now());
        CategoryDto existingCategory = new CategoryDto(1, "category");
        LocalDate start = LocalDate.of(2020, 01, 01);
        LocalDate end = LocalDate.of(2011, 01, 01);
        JobOfferDto newJobOffer = new JobOfferDto(existingCategory, existingUser, start, end);

        assertThrows(IllegalArgumentException.class, () -> jobOfferService.addJobOffer(newJobOffer));
    }

}