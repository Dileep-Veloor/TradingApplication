package com.test.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import com.test.trading.messaging.consumer.MessageChannels;

@SpringBootApplication
@EnableBinding(MessageChannels.class)
public class TradingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TradingApplication.class, args);
	}

}
