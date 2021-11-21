package com.test.trading.service;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.test.trading.messaging.consumer.TradingEventConsumer;
import com.test.trading.messaging.domain.TradingEvent;
import com.test.trading.messaging.domain.TradingEventType;
import com.test.trading.persistence.entity.Event;
import com.test.trading.persistence.entity.Position;
import com.test.trading.persistence.entity.PositionIdentifier;
import com.test.trading.persistence.repository.EventRepository;
import com.test.trading.persistence.repository.PositionRepository;

@Service
public class TradingService {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(TradingService.class);

	private PositionRepository positionRepository;
	private EventRepository eventRepository;
	
	public TradingService(PositionRepository positionRepository, EventRepository eventRepository) {
		this.positionRepository = positionRepository;
		this.eventRepository = eventRepository;
	}
	
	@Transactional
	public void handleTradingEvent(TradingEvent tradingEvent) {
		Optional<Position> position = checkPositionExistForEvent(tradingEvent.getTradingAccount(), tradingEvent.getSecurityIdentifier());
		if(position.isPresent()) {
			createEventAndUpdatePosition(tradingEvent, position.get());
		}else {
			createEventAndPosition(tradingEvent);
		}
	}
	
	private void createEventAndUpdatePosition(TradingEvent tradingEvent, Position position) {
		long updatedQuantity = calculateQuantity(tradingEvent, position.getTotalQuantity());
		eventRepository.save(createEventEntity(tradingEvent));
		position.setTotalQuantity(updatedQuantity);
		positionRepository.save(position) ;
	}
	
	private long calculateQuantity(TradingEvent tradingEvent, long currentQuantity) {
		switch(TradingEventType.valueOf(tradingEvent.getEventType())) {
			case BUY: return currentQuantity + tradingEvent.getQuantity();
			case SELL: return executeSellEvent(tradingEvent,currentQuantity);
			case CANCEL: return executeCancelEvent(tradingEvent, currentQuantity);
			default: return currentQuantity;
		}
	}
	
	private long executeSellEvent(TradingEvent tradingEvent, long currentQuantity) {
		if(currentQuantity < tradingEvent.getQuantity()) {
			LOGGER.error("Cannot process the sell event for trading Id {} ", tradingEvent.getIdentifier());
			throw new TradingException("Cannot process sell the event since Total quantity is less");
		}
		return currentQuantity - tradingEvent.getQuantity();
	}
	private long executeCancelEvent(TradingEvent tradingEvent, long currentQuantity) {
		List<Event> eventList = eventRepository.findByEventId(tradingEvent.getIdentifier());
		if(eventList.size() == 1) {
			return currentQuantity - eventList.get(0).getQuantity() ;
		}else {
			LOGGER.error("Cannot cancel the event for trading Id {} ", tradingEvent.getIdentifier());
			throw new TradingException("Cannot cancel the event");
		}
	}
	
	private Event createEventEntity(TradingEvent tradingEvent) {
		return new Event(tradingEvent.getIdentifier(), 
				tradingEvent.getEventType(),
				tradingEvent.getTradingAccount(),
				tradingEvent.getSecurityIdentifier(),
				tradingEvent.getQuantity());
	}
	
	private Position createPositionEntity(TradingEvent tradingEvent) {
		return new Position(new PositionIdentifier(tradingEvent.getTradingAccount(),
				tradingEvent.getSecurityIdentifier()),
				tradingEvent.getQuantity());
	}
	
	private void createEventAndPosition(TradingEvent tradingEvent) {
		eventRepository.save(createEventEntity(tradingEvent));
		positionRepository.save(createPositionEntity(tradingEvent));
	}
	private Optional<Position> checkPositionExistForEvent(String account, String identifier){
		return positionRepository.findById(new PositionIdentifier(account, identifier));
	}
	
}
