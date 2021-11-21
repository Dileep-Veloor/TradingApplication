package com.test.trading.service;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import com.test.trading.messaging.domain.TradingEvent;
import com.test.trading.persistence.entity.Position;
import com.test.trading.persistence.repository.EventRepository;
import com.test.trading.persistence.repository.PositionRepository;

import static com.test.trading.fixture.TradingFixture.getTradingEvent;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TradingServiceIntegrationTest {
	
	@Autowired
	private TradingService tradingService;
	
	@Autowired
	private PositionService positionService;
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private PositionRepository positionRepository;
	
	private static List<TradingEvent> tradingBuyEvents = Arrays.asList(
			getTradingEvent("1,BUY,ACC1,SEC1,100"),
			getTradingEvent("2,BUY,ACC1,SEC1,50"));
	
	private static List<TradingEvent> tradingBuyDifferentEvents = Arrays.asList(
			getTradingEvent("3,BUY,ACC1,SEC1,12"),
			getTradingEvent("4,BUY,ACC1,SECXYZ,50"),
			getTradingEvent("5,BUY,ACC2,SECXYZ,33"),
			getTradingEvent("6,BUY,ACC1,SEC1,20"));
	
	private static List<TradingEvent> tradingBuyAndSellEvents = Arrays.asList(
			getTradingEvent("10,BUY,ACC1,SEC1,100"),
			getTradingEvent("11,SELL,ACC1,SEC1,50"));
	
	private static List<TradingEvent> tradingCancelEvents = Arrays.asList(
			getTradingEvent("21,BUY,ACC1,SEC1,100"),
			getTradingEvent("21,CANCEL,ACC1,SEC1,0"),
			getTradingEvent("22,BUY,ACC1,SEC1,5"));

	@Test
	@Sql(value= "/delete_records.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void shouldHandleBuyingSameSecurities() {
		tradingBuyEvents.stream().forEach(e -> tradingService.handleTradingEvent(e));
		List<Position> position = positionService.getPositionDetails();
		assertAll(
				() -> assertEquals(1, position.size()),
				() -> assertEquals("ACC1", position.get(0).getPositionId().getTradingAccount()),
				() -> assertEquals("SEC1", position.get(0).getPositionId().getSecurityIdentifier()),
				() -> assertEquals(150, position.get(0).getTotalQuantity()),
				() -> assertEquals(1, position.get(0).getVersion()),
				() -> assertEquals(2, position.get(0).getEventList().size()));
	}
	
	@Test
	@Sql(value= "/delete_records.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void shouldHandleBuyingDifferentSecurities() {
		tradingBuyDifferentEvents.stream().forEach(e -> tradingService.handleTradingEvent(e));
		List<Position> position = positionService.getPositionDetails();
		assertAll(
				() -> assertEquals(3, position.size()),
				() -> assertEquals("ACC1", position.get(0).getPositionId().getTradingAccount()),
				() -> assertEquals("SEC1", position.get(0).getPositionId().getSecurityIdentifier()),
				() -> assertEquals(32, position.get(0).getTotalQuantity()),
				() -> assertEquals(2, position.get(0).getEventList().size()),
				() -> assertEquals("ACC1", position.get(1).getPositionId().getTradingAccount()),
				() -> assertEquals("SECXYZ", position.get(1).getPositionId().getSecurityIdentifier()),
				() -> assertEquals(50, position.get(1).getTotalQuantity()),
				() -> assertEquals(1, position.get(1).getEventList().size()),
				() -> assertEquals("ACC2", position.get(2).getPositionId().getTradingAccount()),
				() -> assertEquals("SECXYZ", position.get(2).getPositionId().getSecurityIdentifier()),
				() -> assertEquals(33, position.get(2).getTotalQuantity()),
				() -> assertEquals(1, position.get(2).getEventList().size()));
	}
	
	@Test
	@Sql(value= "/delete_records.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void shouldHandleBuyingAndSellingSecurities() {
		tradingBuyAndSellEvents.stream().forEach(e -> tradingService.handleTradingEvent(e));
		List<Position> position = positionService.getPositionDetails();
		assertAll(
				() -> assertEquals(1, position.size()),
				() -> assertEquals("ACC1", position.get(0).getPositionId().getTradingAccount()),
				() -> assertEquals("SEC1", position.get(0).getPositionId().getSecurityIdentifier()),
				() -> assertEquals(50, position.get(0).getTotalQuantity()),
				() -> assertEquals(2, position.get(0).getEventList().size()));
	}
	
	@Test
	@Sql(value= "/delete_records.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
	void shouldHandleCancelSecurities() {
		tradingCancelEvents.stream().forEach(e -> tradingService.handleTradingEvent(e));
		List<Position> position = positionService.getPositionDetails();
		assertAll(
				() -> assertEquals(1, position.size()),
				() -> assertEquals("ACC1", position.get(0).getPositionId().getTradingAccount()),
				() -> assertEquals("SEC1", position.get(0).getPositionId().getSecurityIdentifier()),
				() -> assertEquals(5, position.get(0).getTotalQuantity()),
				() -> assertEquals(3, position.get(0).getEventList().size()));
	}
	

}
