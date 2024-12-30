package io.sambrolia.sprintcapacity.controller;

import io.sambrolia.sprintcapacity.entity.WorkDay;
import io.sambrolia.sprintcapacity.entity.TeamMember;
import io.sambrolia.sprintcapacity.repository.WorkDayRepository;
import io.sambrolia.sprintcapacity.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Controller
public class SummaryController {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @Autowired
    private WorkDayRepository workdayRepository;

    @GetMapping("/summary")
    public String showSummary(Model model) {
        List<TeamMember> teamMembers = teamMemberRepository.findAll();
        List<WorkDay> workdays = workdayRepository.findAll();

        AtomicInteger totalCapacity = new AtomicInteger();

        var summaries = teamMembers.stream().map(member -> {
            long workDaysCount = workdays.stream()
                    .filter(w -> w.getMember().getId().equals(member.getId()))
                    .count();

            int totalHours = (int) (workDaysCount * member.getWorkHoursPerDay());
            totalCapacity.addAndGet(totalHours);

            return new MemberSummary(member.getName(), member.getWorkHoursPerDay(), workDaysCount, totalHours);
        }).collect(Collectors.toList());

        model.addAttribute("totalCapacity", totalCapacity);
        model.addAttribute("summaries", summaries);
        return "summary";
    }

    private static class MemberSummary {
        private final String name;
        private final int workHoursPerDay;
        private final long totalWorkDays;
        private final int totalHours;

        public MemberSummary(String name, int workHoursPerDay, long totalWorkDays, int totalHours) {
            this.name = name;
            this.workHoursPerDay = workHoursPerDay;
            this.totalWorkDays = totalWorkDays;
            this.totalHours = totalHours;
        }

        public String getName() { return name; }
        public int getWorkHoursPerDay() { return workHoursPerDay; }
        public long getTotalWorkDays() { return totalWorkDays; }
        public int getTotalHours() { return totalHours; }
    }
}
