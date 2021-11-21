package com.test.trading.messaging.consumer;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface MessageChannels {
	
	@Input("trading-input")
	SubscribableChannel tradingInput();

}
