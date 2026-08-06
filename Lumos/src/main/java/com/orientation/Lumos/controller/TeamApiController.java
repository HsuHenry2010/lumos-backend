package com.orientation.Lumos.controller;

import com.orientation.Lumos.model.Team;
import com.orientation.Lumos.repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TeamApiController {

    @Autowired
    private TeamRepository teamRepository;

    @GetMapping("/api/teams")
    public List<Team> getTeams() {
        return teamRepository.findAll();
    }
}