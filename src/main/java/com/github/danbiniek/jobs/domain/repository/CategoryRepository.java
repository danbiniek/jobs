package com.github.danbiniek.jobs.domain.repository;

import com.github.danbiniek.jobs.domain.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
