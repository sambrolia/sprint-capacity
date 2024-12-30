package io.sambrolia.sprintcapacity.controller;

import io.sambrolia.sprintcapacity.entity.TeamMember;
import io.sambrolia.sprintcapacity.repository.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/team-members")
public class TeamMemberController {

    @Autowired
    private TeamMemberRepository teamMemberRepository;

    @GetMapping
    public String listTeamMembers(Model model) {
        model.addAttribute("teamMembers", teamMemberRepository.findAll());
        model.addAttribute("pageTitle", "Team Members");
        return "team-members";
    }

    @PostMapping
    public String addTeamMember(@ModelAttribute TeamMember teamMember) {
        teamMemberRepository.save(teamMember);
        return "redirect:/team-members";
    }

    @PostMapping("/{id}")
    public String updateTeamMember(@PathVariable Long id, @ModelAttribute TeamMember updatedMember) {
        teamMemberRepository.findById(id).ifPresent(existingMember -> {
            existingMember.setName(updatedMember.getName());
            existingMember.setWorkHoursPerDay(updatedMember.getWorkHoursPerDay());
            teamMemberRepository.save(existingMember);
        });
        return "redirect:/team-members";
    }

    @PostMapping("/{id}/delete")
    public String deleteTeamMember(@PathVariable Long id) {
        teamMemberRepository.deleteById(id); // This will cascade and remove associated workdays.
        return "redirect:/team-members";
    }
}
