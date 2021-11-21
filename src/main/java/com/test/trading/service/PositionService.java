package com.test.trading.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.test.trading.persistence.entity.Event;
import com.test.trading.persistence.entity.Position;
import com.test.trading.persistence.repository.EventRepository;
import com.test.trading.persistence.repository.PositionRepository;

@Service
public class PositionService {
	
	private PositionRepository positionRepository;
	private EventRepository eventRepository;
	
	public PositionService(PositionRepository positionRepository, EventRepository eventRepository) {
		this.positionRepository = positionRepository;
		this.eventRepository = eventRepository;
	}
	
	public List<Position> getPositionDetails(){
		List<Position> positionList = positionRepository.findAll();
		for(Position p: positionList) {
			p.setEventList(findEventsAssociatedWithPosition(p));
		}
		return positionList;
	}
	
	private List<Event> findEventsAssociatedWithPosition(Position position){
		return eventRepository.findByTradingAccountAndSecurityIdentifier(
				position.getPositionId().getTradingAccount(), 
				position.getPositionId().getSecurityIdentifier());
	}

}
