package com.test.trading.fixture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.test.trading.controller.dto.EventJson;
import com.test.trading.controller.dto.PositionJson;
import com.test.trading.messaging.domain.TradingEvent;
import com.test.trading.messaging.domain.TradingEventType;
import com.test.trading.persistence.entity.Event;
import com.test.trading.persistence.entity.Position;
import com.test.trading.persistence.entity.PositionIdentifier;

public class TradingFixture {

	public static List<PositionJson> getPositionJson() {
		List<EventJson> eventJsonList = new ArrayList<EventJson>();
		eventJsonList.add(new EventJson("1", TradingEventType.BUY.name(), "AC1", "ST1", 10));
		eventJsonList.add(new EventJson("2", TradingEventType.BUY.name(), "AC1", "ST1", 15));
		PositionJson positionJson = new PositionJson("AC1", "ST1", 25, eventJsonList);
		return Arrays.asList(positionJson);
	}
	
	public static Event getEvent(String eventId, String type, String account, String identifier, long quantity) {
		return new Event(eventId, type, account, identifier, quantity);
	}
	
	public static List<Position> getPosition() {
		List<Event> eventList = new ArrayList<Event>();
		eventList.add(getEvent("1", TradingEventType.BUY.name(),"AC1","ST1",10));
		eventList.add(getEvent("2", TradingEventType.BUY.name(),"AC1","ST1",15));
		List<Event> eventList2 = new ArrayList<Event>();
		eventList2.add(getEvent("1", TradingEventType.BUY.name(),"AC2","ST2",150));
		eventList2.add(getEvent("2", TradingEventType.SELL.name(),"AC2","ST2",50));
		Position position1 = new Position(new PositionIdentifier("AC1","ST1"),25);
		position1.setEventList(eventList);
		Position position2 = new Position(new PositionIdentifier("AC2","ST2"),100);
		position2.setEventList(eventList2);
		return Arrays.asList(position1, position2);
	}
	
	public static TradingEvent getTradingEvent(String event) {
		String[] events = event.split(",");
		return TradingEvent.builder().identifier(events[0]).eventType(events[1])
				.tradingAccount(events[2]).securityIdentifier(events[3])
				.quantity(Long.parseLong(events[4])).build();
	}
}
