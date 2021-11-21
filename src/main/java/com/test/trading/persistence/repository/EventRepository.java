package com.test.trading.persistence.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.trading.persistence.entity.Event;

@Repository
public interface EventRepository extends JpaRepository<Event,String>{
	
	List<Event> findByEventId(String identifier);
	
	List<Event> findByTradingAccountAndSecurityIdentifier(String tradingAccount, String securityIdentifier);

}
