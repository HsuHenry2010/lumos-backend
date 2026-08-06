package com.orientation.Lumos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "fortresses")
public class Fortress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code; // 堡壘代號，例如: A, B, C, D
    private int defensePower; // 堡壘專屬屬性或防禦力

    // 多對一：一個堡壘可能被某個小隊佔領（若為 null 則代表無人佔領）
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // 堡壘自己的獨立邏輯程式碼
    public void performFortressLogic() {
        // 這裡可以寫堡壘觸發的特殊事件、資源產出或防禦計算邏輯
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public int getDefensePower() { return defensePower; }
    public void setDefensePower(int defensePower) { this.defensePower = defensePower; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
}