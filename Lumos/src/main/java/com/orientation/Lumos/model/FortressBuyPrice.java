package com.orientation.Lumos.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "fortress_buy_prices")
public class FortressBuyPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fortress_id")
    private Fortress fortress;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer phase;

    private Integer price;

    public FortressBuyPrice() {
    }

    // 靜態輔助方法：改用基本數值 (.longValue() / .intValue()) 進行嚴格比對，解決型別比對失敗顯示「未定」的問題
    public static Integer findPrice(List<FortressBuyPrice> list, Long fortressId, Long itemId, Integer phase) {
        if (list == null || fortressId == null || itemId == null || phase == null) {
            return null;
        }
        for (FortressBuyPrice bp : list) {
            if (bp != null && bp.getFortress() != null && bp.getFortress().getId() != null &&
                    bp.getItem() != null && bp.getItem().getId() != null &&
                    bp.getPhase() != null) {

                if (bp.getFortress().getId().longValue() == fortressId.longValue() &&
                        bp.getItem().getId().longValue() == itemId.longValue() &&
                        bp.getPhase().intValue() == phase.intValue()) {
                    return bp.getPrice();
                }
            }
        }
        return null;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Fortress getFortress() {
        return fortress;
    }

    public void setFortress(Fortress fortress) {
        this.fortress = fortress;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Integer getPhase() {
        return phase;
    }

    public void setPhase(Integer phase) {
        this.phase = phase;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}