package com.orientation.Lumos.repository;

import com.orientation.Lumos.model.FortressBuyPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FortressBuyPriceRepository extends JpaRepository<FortressBuyPrice, Long> {

    // 改為 Optional<FortressBuyPrice>
    Optional<FortressBuyPrice> findByFortressIdAndItemIdAndPhase(Long fortressId, Long itemId, Integer phase);
}