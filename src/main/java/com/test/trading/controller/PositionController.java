package com.test.trading.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.test.trading.controller.dto.EventJson;
import com.test.trading.controller.dto.PositionJson;
import com.test.trading.persistence.entity.Event;
import com.test.trading.persistence.entity.Position;
import com.test.trading.service.PositionService;

@RestController
public class PositionController {
	
	private final PositionService positionService;
	
	public PositionController(PositionService positionService) {
		this.positionService = positionService;
	}

	@GetMapping(path="/position", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public List<PositionJson> getPositionDetails(){
		return positionService.getPositionDetails().stream().map(this::convertToPositionDto).collect(Collectors.toList());
	}
	
	private PositionJson convertToPositionDto(Position position) {
		List<EventJson> eventJsonList = position.getEventList().stream().map(this::convertToEventDto).collect(Collectors.toList());
		return new PositionJson(
				position.getPositionId().getTradingAccount(),
				position.getPositionId().getSecurityIdentifier(),
				position.getTotalQuantity(),
				eventJsonList);
	}

	private EventJson convertToEventDto(Event event) {
		return new EventJson(event.getEventId(), event.getEventType(), event.getTradingAccount(), 
				event.getSecurityIdentifier(), event.getQuantity());
	}
}
