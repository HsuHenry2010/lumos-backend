package com.orientation.Lumos.repository;

import com.orientation.Lumos.model.Fortress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FortressRepository extends JpaRepository<Fortress, Long> {
}