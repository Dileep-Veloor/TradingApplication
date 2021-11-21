package com.test.trading.messaging.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.Message;

import com.test.trading.messaging.domain.TradingEvent;
import com.test.trading.service.TradingService;

public class TradingEventConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TradingEventConsumer.class);

	private TradingService tradingService;

	public TradingEventConsumer(TradingService tradingService) {
		this.tradingService = tradingService;
	}
	
	@StreamListener("trading-input")
	public void tradingInput(Message<TradingEvent> message) {
		LOGGER.debug("Message Headers: {}" , message.getHeaders());
		LOGGER.debug("Message Payload: {}" , message.getPayload());
		tradingService.handleTradingEvent(message.getPayload());
	}
	
}
