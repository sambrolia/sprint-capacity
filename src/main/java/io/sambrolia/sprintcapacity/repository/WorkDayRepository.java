package io.sambrolia.sprintcapacity.repository;

import io.sambrolia.sprintcapacity.entity.WorkDay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface WorkDayRepository extends JpaRepository<WorkDay, Long> {

    boolean existsByMemberIdAndDate(Long memberId, LocalDate date);
}
