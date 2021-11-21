package com.test.trading.persistence;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.test.trading.fixture.TradingFixture;
import com.test.trading.messaging.domain.TradingEventType;
import com.test.trading.persistence.entity.Event;
import com.test.trading.persistence.repository.EventRepository;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataJpaTest
public class EventDataTest {

	@Autowired
	EventRepository eventRepository;
	
	@Test
	public void createEvent() {
		Event event = TradingFixture.getEvent("1", TradingEventType.BUY.name(),"AC1","ST1",10);
		eventRepository.save(event);
		eventRepository.save(event);
		List<Event> transactionFromDatabase = eventRepository.findByTradingAccountAndSecurityIdentifier(
				event.getTradingAccount(), event.getSecurityIdentifier());
		assertThat(transactionFromDatabase.size()).isEqualTo(1);
	}
}
