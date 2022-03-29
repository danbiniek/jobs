package com.github.danbiniek.jobs.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@ToString
@Getter
@Entity
@Table(name = "job_offers")
@NoArgsConstructor
@AllArgsConstructor
public class JobOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Setter
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    @Setter
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private User employer;
    private LocalDate startDate;
    private LocalDate endDate;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        JobOffer jobOffer = (JobOffer) o;
        return Objects.equals(id, jobOffer.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
