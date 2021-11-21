package com.test.trading.messaging.domain;

import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString @Builder
public class TradingEvent {

	@NotNull
	private String identifier;
	@NotNull
	private String eventType;
	@NotNull
	private String tradingAccount;
	@NotNull
	private String securityIdentifier;
	private long quantity;

}
