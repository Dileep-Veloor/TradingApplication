package com.test.trading.messaging.consumer;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.apache.kafka.common.Uuid;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.test.trading.fixture.TradingFixture;
import com.test.trading.messaging.domain.TradingEvent;
import com.test.trading.service.TradingService;

@ExtendWith(SpringExtension.class)
public class TradingEventConsumerTest {

	@Mock
	TradingService tradingService;
	
	@InjectMocks
	TradingEventConsumer tradingEventConsumer;
	
	private static Message<TradingEvent> tradingEvent = MessageBuilder.withPayload(TradingFixture.getTradingEvent("1,BUY,AC1,S1,50"))
			.setHeader(KafkaHeaders.CORRELATION_ID,Uuid.randomUuid().toString()).build();
	
	@Test
	public void tradingEvent_to_process_the_message() {
		tradingEventConsumer.tradingInput(tradingEvent);
		verify(tradingService, times(1)).handleTradingEvent(tradingEvent.getPayload());
	}
}
