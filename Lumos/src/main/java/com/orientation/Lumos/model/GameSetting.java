package com.orientation.Lumos.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "game_settings")
public class GameSetting {

    @Id
    private Long id = 1L; // 固定使用 ID = 1 作為全站唯一的活動階段設定

    private Integer currentPhase = 1; // 目前活動階段 (1, 2, 3, 4)

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Integer getCurrentPhase() { return currentPhase; }
    public void setCurrentPhase(Integer currentPhase) { this.currentPhase = currentPhase; }
}