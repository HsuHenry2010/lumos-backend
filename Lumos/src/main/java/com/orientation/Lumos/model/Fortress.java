package com.orientation.Lumos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fortresses")
public class Fortress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;

    // 目前佔領的小隊
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team occupyingTeam;

    // 堡壘固定販賣商品
    @ManyToOne
    @JoinColumn(name = "sell_item_id")
    private Item sellItem;

    // 販賣價格
    private Integer sellPrice;

    public Fortress() {
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Team getOccupyingTeam() {
        return occupyingTeam;
    }

    public void setOccupyingTeam(Team occupyingTeam) {
        this.occupyingTeam = occupyingTeam;
    }

    public Item getSellItem() {
        return sellItem;
    }

    public void setSellItem(Item sellItem) {
        this.sellItem = sellItem;
    }

    public Integer getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Integer sellPrice) {
        this.sellPrice = sellPrice;
    }
}