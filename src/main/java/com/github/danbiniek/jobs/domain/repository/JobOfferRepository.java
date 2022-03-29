package com.github.danbiniek.jobs.domain.repository;

import com.github.danbiniek.jobs.domain.entity.JobOffer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobOfferRepository extends JpaRepository<JobOffer, Integer> {
}
