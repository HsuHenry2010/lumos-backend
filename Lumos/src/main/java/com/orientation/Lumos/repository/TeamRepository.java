package com.orientation.Lumos.repository;

import com.orientation.Lumos.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // 自動依照分數由高到低排序小隊
    List<Team> findAllByOrderByScoreDesc();
}