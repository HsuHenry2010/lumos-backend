package com.orientation.Lumos.controller;

import com.orientation.Lumos.model.Team;
import com.orientation.Lumos.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/scoreboard")
    public String staffScoreboard(Model model) {
        // 取得所有小隊並依分數由高到低排序
        List<Team> teams = teamRepository.findAllByOrderByScoreDesc();
        model.addAttribute("teams", teams);
        return "staff-scoreboard";
    }

    @PostMapping("/update-score")
    public String updateScore(@RequestParam Long teamId, @RequestParam int points) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team != null) {
            int currentScore = team.getScore() != null ? team.getScore() : 0;
            // 累加或扣除分數
            team.setScore(currentScore + points);
            teamRepository.save(team);
        }
        return "redirect:/staff/scoreboard";
    }
}