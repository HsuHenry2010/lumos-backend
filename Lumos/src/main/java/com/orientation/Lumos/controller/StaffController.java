package com.orientation.Lumos.controller;

import com.orientation.Lumos.model.*;
import com.orientation.Lumos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
public class StaffController {

    @Autowired
    private GameSettingRepository gameSettingRepository;

    @Autowired
    private FortressRepository fortressRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private FortressBuyPriceRepository buyPriceRepository;

    // ==========================================
    // 1. 關主堡壘管理頁面 (staff-fortress.html)
    // ==========================================

    @GetMapping({"/staff-fortress", "/staff/fortress"})
    public String staffFortressPage(Model model) {
        GameSetting setting = gameSettingRepository.findById(1L).orElseGet(GameSetting::new);
        Integer currentPhase = setting.getCurrentPhase() != null ? setting.getCurrentPhase() : 1;

        model.addAttribute("currentPhase", currentPhase);
        model.addAttribute("fortresses", fortressRepository.findAll());
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("buyPrices", buyPriceRepository.findAll());

        return "staff-fortress";
    }

    // AJAX：更新堡壘佔領狀態（點擊更新畫面不跳動）
    @PostMapping("/staff/update-occupy")
    @ResponseBody
    public String updateOccupy(@RequestParam Long fortressId,
                               @RequestParam(required = false) Long teamId) {
        Fortress fortress = fortressRepository.findById(fortressId).orElse(null);
        if (fortress != null) {
            if (teamId == null || teamId == -1) {
                fortress.setOccupyingTeam(null);
            } else {
                Team team = teamRepository.findById(teamId).orElse(null);
                fortress.setOccupyingTeam(team);
            }
            fortressRepository.save(fortress);
        }
        return "success";
    }

    // API：提供給 staff-fortress.html 每 3 秒自動輪詢抓取最新階段與行情
    @GetMapping("/staff-fortress/api/data")
    @ResponseBody
    public Map<String, Object> getFortressDataApi() {
        GameSetting setting = gameSettingRepository.findById(1L).orElseGet(GameSetting::new);
        Integer currentPhase = setting.getCurrentPhase() != null ? setting.getCurrentPhase() : 1;

        List<Fortress> fortresses = fortressRepository.findAll();
        List<Item> items = itemRepository.findAll();
        List<FortressBuyPrice> buyPrices = buyPriceRepository.findAll();

        List<Map<String, Object>> fortressList = new ArrayList<>();
        for (Fortress f : fortresses) {
            Map<String, Object> fMap = new HashMap<>();
            fMap.put("id", f.getId());
            fMap.put("code", f.getCode());
            fMap.put("occupyingTeam", f.getOccupyingTeam() != null ? f.getOccupyingTeam().getName() : "無人佔領");

            // 販賣商品資訊
            if (f.getSellItem() != null && f.getSellPrice() != null) {
                fMap.put("sellInfo", f.getSellItem().getName() + " ($" + f.getSellPrice() + ")");
            } else {
                fMap.put("sellInfo", "無販賣商品");
            }

            // 當前階段收購行情
            List<Map<String, Object>> buyPriceList = new ArrayList<>();
            for (Item item : items) {
                Integer price = FortressBuyPrice.findPrice(buyPrices, f.getId(), item.getId(), currentPhase);
                Map<String, Object> bpMap = new HashMap<>();
                bpMap.put("itemName", item.getName());
                bpMap.put("price", price != null ? price.toString() : "未定");
                buyPriceList.add(bpMap);
            }
            fMap.put("buyPrices", buyPriceList);
            fortressList.add(fMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("currentPhase", currentPhase);
        response.put("fortresses", fortressList);
        return response;
    }

    // ==========================================
    // 2. 關主道具發放頁面 (staff-item.html)
    // ==========================================

    @GetMapping({"/staff-item", "/staff/item"})
    public String staffItemPage(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("items", itemRepository.findAll());
        return "staff-item";
    }

    // ==========================================
    // 3. 關主計分板頁面 (staff-scoreboard.html)
    // ==========================================

    @GetMapping({"/staff-scoreboard", "/staff/scoreboard"})
    public String staffScoreboardPage(Model model) {
        model.addAttribute("teams", teamRepository.findAll());
        return "staff-scoreboard";
    }

    // 處理小隊加扣分請求的 AJAX 端點
    @PostMapping("/staff/update-score")
    @ResponseBody
    public String updateScore(@RequestParam Long teamId, @RequestParam int points) {
        Team team = teamRepository.findById(teamId).orElse(null);
        if (team != null) {
            int currentScore = team.getScore() != null ? team.getScore() : 0;
            team.setScore(currentScore + points);
            teamRepository.save(team);
            return "success";
        }
        return "error";
    }
}