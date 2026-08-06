package com.orientation.Lumos.model;

import jakarta.persistence.*;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name; // 物品名稱（四種道具之一）

    // 多對一：一個物品可能被某個小隊攜帶（若為 null 則代表無人攜帶）
    @ManyToOne
    @JoinColumn(name = "team_id")
    private Team team;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Team getTeam() { return team; }
    public void setTeam(Team team) { this.team = team; }
}