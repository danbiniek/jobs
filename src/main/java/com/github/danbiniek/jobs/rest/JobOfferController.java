package com.github.danbiniek.jobs.rest;

import com.github.danbiniek.jobs.api.JobOfferService;
import com.github.danbiniek.jobs.api.dto.JobOfferDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/job-offers")
public class JobOfferController {

    private final JobOfferService jobOfferService;

    @GetMapping
    public List<JobOfferDto> listPagedJobOffers(@RequestParam(defaultValue = "100") int size, @RequestParam(defaultValue = "0") int page) {
        return jobOfferService.getAllJobOffers(page, size);
    }

    @PostMapping
    public JobOfferDto addJobOffer(@RequestBody JobOfferDto jobOfferDto) {
        return jobOfferService.addJobOffer(jobOfferDto);
    }
}
