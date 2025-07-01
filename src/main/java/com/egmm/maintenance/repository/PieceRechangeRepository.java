package com.egmm.maintenance.repository;

import com.egmm.maintenance.entity.PieceRechange;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PieceRechangeRepository extends JpaRepository<PieceRechange, Long> {
}