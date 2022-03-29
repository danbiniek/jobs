package com.github.danbiniek.jobs.api;

import com.github.danbiniek.jobs.api.dto.JobOfferDto;

import java.util.List;

public interface JobOfferService {

    List<JobOfferDto> getAllJobOffers(int page, int size);

    JobOfferDto addJobOffer(JobOfferDto newJobOffer);

}
