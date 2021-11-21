package com.test.trading.controller.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter @AllArgsConstructor @Setter
public class EventJson {
	
	private String id;
	private String type;
	private String account;
	private String securityId;
	private long quantity;
}
