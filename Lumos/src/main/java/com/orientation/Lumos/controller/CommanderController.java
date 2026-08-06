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

    // 更新堡壘固定販賣商品與售價
    @PostMapping("/commander/update-sell-price")
    @ResponseBody
    public String updateSellPrice(@RequestParam Long fortressId,
                                  @RequestParam Long sellItemId,
                                  @RequestParam(required = false) Integer sellPrice) {
        Fortress fortress = fortressRepository.findById(fortressId).orElse(null);
        if (fortress == null) {
            return "找不到該堡壘";
        }

        if (sellItemId == null || sellItemId == -1) {
            fortress.setSellItem(null);
            fortress.setSellPrice(null);
        } else {
            Item item = itemRepository.findById(sellItemId).orElse(null);
            fortress.setSellItem(item);
            fortress.setSellPrice(sellPrice);
        }

        fortressRepository.save(fortress);
        return "success";
    }

    // ⭐ 修正這裡：加上 /commander 前綴，讓前端抓得到網址
    @PostMapping("/commander/api/update-buy-price")
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