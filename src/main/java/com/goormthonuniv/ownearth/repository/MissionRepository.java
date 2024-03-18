package com.goormthonuniv.ownearth.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goormthonuniv.ownearth.domain.Mission;

public interface MissionRepository extends JpaRepository<Mission, Long> {}
