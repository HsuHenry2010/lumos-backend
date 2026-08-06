package com.orientation.Lumos.repository;

import com.orientation.Lumos.model.GameSetting;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameSettingRepository extends JpaRepository<GameSetting, Long> {
}