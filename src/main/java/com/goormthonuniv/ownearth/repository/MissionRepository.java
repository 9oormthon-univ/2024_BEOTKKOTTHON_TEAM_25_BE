package com.goormthonuniv.ownearth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goormthonuniv.ownearth.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {
  @Query(value = "SELECT * FROM mission ORDER BY RAND() LIMIT 1", nativeQuery = true)
  Mission findRandomMission();
}
