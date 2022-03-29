package com.github.danbiniek.jobs.api.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Objects;

@ToString
@Getter
@AllArgsConstructor
public class JobOfferDto {

    private Integer id;
    private CategoryDto category;
    private UserDto employer;
    private LocalDate startDate;
    private LocalDate endDate;

    @JsonCreator
    public JobOfferDto(CategoryDto category, UserDto employer, LocalDate startDate, LocalDate endDate) {
        Objects.requireNonNull(category);
        Objects.requireNonNull(employer);
        Objects.requireNonNull(startDate);
        Objects.requireNonNull(endDate);
        this.category = category;
        this.employer = employer;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
