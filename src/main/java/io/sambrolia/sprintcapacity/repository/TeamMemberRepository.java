package io.sambrolia.sprintcapacity.repository;

import io.sambrolia.sprintcapacity.entity.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {}
