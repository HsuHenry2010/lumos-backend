package com.orientation.Lumos.controller;

import com.orientation.Lumos.model.Fortress;
import com.orientation.Lumos.model.Item;
import com.orientation.Lumos.model.Team;
import com.orientation.Lumos.repository.FortressRepository;
import com.orientation.Lumos.repository.ItemRepository;
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

    @Autowired
    private FortressRepository fortressRepository;

    @Autowired
    private ItemRepository itemRepository;

    // 1. 小隊分數管理分頁
    @GetMapping("/scoreboard")
    public String staffScoreboard(Model model) {
        List<Team> teams = teamRepository.findAllByOrderByScoreDesc();
        model.addAttribute("teams", teams);
        return "staff-scoreboard";
    }

    @PostMapping("/update-score")
    public String updateScore(@RequestParam Long teamId, @RequestParam int points) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team != null) {
            int currentScore = team.getScore() != null ? team.getScore() : 0;
            team.setScore(currentScore + points);
            teamRepository.save(team);
        }
        return "redirect:/staff/scoreboard";
    }

    // 2. 堡壘管理分頁
    @GetMapping("/fortress")
    public String staffFortress(Model model) {
        List<Team> teams = teamRepository.findAll();
        List<Fortress> fortresses = fortressRepository.findAll();
        model.addAttribute("teams", teams);
        model.addAttribute("fortresses", fortresses);
        return "staff-fortress";
    }

    @PostMapping("/update-fortress")
    public String updateFortress(@RequestParam Long fortressId, @RequestParam(required = false) Long teamId) {
        Fortress fortress = fortressRepository.findById(fortressId).orElse(null);
        if (fortress != null) {
            if (teamId == null || teamId == -1) {
                fortress.setTeam(null); // 解除佔領
            } else {
                Team team = teamRepository.findById(teamId).orElse(null);
                fortress.setTeam(team);
                fortress.performFortressLogic(); // 執行堡壘自己的獨立邏輯
            }
            fortressRepository.save(fortress);
        }
        return "redirect:/staff/fortress";
    }

    // 3. 道具管理分頁
    @GetMapping("/item")
    public String staffItem(Model model) {
        List<Team> teams = teamRepository.findAll();
        List<Item> items = itemRepository.findAll();
        model.addAttribute("teams", teams);
        model.addAttribute("items", items);
        return "staff-item";
    }

    @PostMapping("/update-item")
    public String updateItem(@RequestParam Long itemId, @RequestParam(required = false) Long teamId) {
        Item item = itemRepository.findById(itemId).orElse(null);
        if (item != null) {
            if (teamId == null || teamId == -1) {
                item.setTeam(null); // 卸下物品
            } else {
                Team team = teamRepository.findById(teamId).orElse(null);
                item.setTeam(team); // 小隊帶走物品
            }
            itemRepository.save(item);
        }
        return "redirect:/staff/item";
    }
}