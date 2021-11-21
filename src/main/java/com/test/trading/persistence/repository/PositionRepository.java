package com.test.trading.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.trading.persistence.entity.Position;
import com.test.trading.persistence.entity.PositionIdentifier;

@Repository
public interface PositionRepository extends JpaRepository<Position,PositionIdentifier>{

}
