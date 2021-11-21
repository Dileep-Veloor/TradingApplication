package com.test.trading.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static com.test.trading.fixture.TradingFixture.getTradingEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.test.trading.messaging.domain.TradingEvent;
import com.test.trading.persistence.entity.Event;
import com.test.trading.persistence.entity.Position;
import com.test.trading.persistence.entity.PositionIdentifier;
import com.test.trading.persistence.repository.EventRepository;
import com.test.trading.persistence.repository.PositionRepository;


@ExtendWith(SpringExtension.class)
public class TradingServiceTest {
	
	@Mock
	private EventRepository eventRepository;
	
	@Mock
	private PositionRepository positionRepository;
	
	@InjectMocks
	private TradingService tradingService;
	
	
	@ParameterizedTest
	@MethodSource("tradingEvents")
	void perform_trading_events_success_with_validation(TradingEvent tradingEvent) {
		when(positionRepository.findById(Mockito.any(PositionIdentifier.class))).thenReturn(Optional.empty());
		tradingService.handleTradingEvent(tradingEvent);
		verify(eventRepository, times(1)).save(Mockito.any(Event.class));
		verify(positionRepository, times(1)).save(Mockito.any(Position.class));
	}
	
	private static List<TradingEvent> tradingEvents(){
		return Arrays.asList(
				getTradingEvent("1,BUY,AC1,SC1,50"),
				getTradingEvent("2,BUY,AC2,SC2,100"));
	}

}
