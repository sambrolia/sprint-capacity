package io.sambrolia.sprintcapacity.controller;

import io.sambrolia.sprintcapacity.entity.TeamMember;
import io.sambrolia.sprintcapacity.entity.WorkDay;
import io.sambrolia.sprintcapacity.repository.WorkDayRepository;
import io.sambrolia.sprintcapacity.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/workdays")
public class WorkDayController {

    @Autowired
    private WorkDayRepository workDayRepository;

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @GetMapping
    public String listWorkdays(Model model) {
        List<WorkDay> workDays = workDayRepository.findAll();
        model.addAttribute("workdays", workDays);
        model.addAttribute("members", teamMemberRepository.findAll());
        model.addAttribute("pageTitle", "Workdays");
        return "workdays";
    }

    @PostMapping
    public String addWorkday(@RequestParam Long member_id, @RequestParam String date, Model model) {
        Optional<TeamMember> teamMemberOpt = teamMemberRepository.findById(member_id);
        if (teamMemberOpt.isEmpty()) {
            model.addAttribute("error", "Invalid team member selected.");
            model.addAttribute("members", teamMemberRepository.findAll());
            model.addAttribute("workdays", workDayRepository.findAll());
            return "workdays";
        }

        TeamMember teamMember = teamMemberOpt.get();
        WorkDay workDay = new WorkDay();
        workDay.setMember(teamMember);
        workDay.setDate(LocalDate.parse(date));

        workDayRepository.save(workDay);
        return "redirect:/workdays";
    }


    @PostMapping("/{id}/delete")
    public String deleteWorkday(@PathVariable Long id) {
        workDayRepository.deleteById(id);
        return "redirect:/workdays";
    }
}
