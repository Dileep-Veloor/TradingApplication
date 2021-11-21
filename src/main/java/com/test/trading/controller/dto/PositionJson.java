package com.test.trading.controller.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import lombok.AllArgsConstructor;

@Getter @AllArgsConstructor @Setter
public class PositionJson {
	
	private String tradingAccount;
	private String securityIdentifier;
	private long totalQuantity;
	private List<EventJson> eventList;

}
