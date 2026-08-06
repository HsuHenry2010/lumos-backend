package com.orientation.Lumos.controller;

import com.orientation.Lumos.model.*;
import com.orientation.Lumos.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class CommanderController {

    @Autowired
    private GameSettingRepository gameSettingRepository;

    @Autowired
    private FortressRepository fortressRepository;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private FortressBuyPriceRepository buyPriceRepository;

    @GetMapping("/commander")
    public String commanderPage(Model model) {
        GameSetting setting = gameSettingRepository.findById(1L).orElseGet(GameSetting::new);
        model.addAttribute("currentPhase", setting.getCurrentPhase() != null ? setting.getCurrentPhase() : 1);
        model.addAttribute("fortresses", fortressRepository.findAll());
        model.addAttribute("items", itemRepository.findAll());
        model.addAttribute("buyPrices", buyPriceRepository.findAll());
        return "commander";
    }

    // 變更遊戲階段
    @PostMapping("/commander/update-phase")
    @ResponseBody
    public String updatePhase(@RequestParam Integer phase) {
        if (phase == null || phase < 1) return "無效的階段";
        GameSetting setting = gameSettingRepository.findById(1L).orElseGet(GameSetting::new);
        setting.setCurrentPhase(phase);
        gameSettingRepository.save(setting);
        return "success";
    }

    // 更新收購行情（加入 required = false 防止空字串崩潰）
    @PostMapping("/api/update-buy-price")
    @ResponseBody
    public String updateBuyPrice(@RequestParam Long fortressId,
                                 @RequestParam Long itemId,
                                 @RequestParam Integer phase,
                                 @RequestParam(required = false) Integer price) {
        if (price == null) {
            return "價格不能為空";
        }
        if (price < 0) {
            return "價格不能為負數";
        }

        try {
            Optional<FortressBuyPrice> existing = buyPriceRepository
                    .findByFortressIdAndItemIdAndPhase(fortressId, itemId, phase);

            FortressBuyPrice buyPrice;
            if (existing.isPresent()) {
                buyPrice = existing.get();
            } else {
                buyPrice = new FortressBuyPrice();
                Fortress f = fortressRepository.findById(fortressId).orElse(null);
                Item i = itemRepository.findById(itemId).orElse(null);
                buyPrice.setFortress(f);
                buyPrice.setItem(i);
                buyPrice.setPhase(phase);
            }

            buyPrice.setPrice(price);
            buyPriceRepository.save(buyPrice);
            return "success";
        } catch (Exception e) {
            return "儲存失敗：" + e.getMessage();
        }
    }
}