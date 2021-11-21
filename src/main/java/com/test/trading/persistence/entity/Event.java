package com.test.trading.persistence.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.kafka.common.Uuid;

@Entity
@Table(name="T_EVENT")
public class Event {
	
	@Id
	@Column(name="EVENT_UUID")
	private String id = Uuid.randomUuid().toString();
	
	@Column(name="EVENT_ID")
	private String eventId;
	
	@Column(name="EVENT_TYPE")
	private String eventType;
	
	@Column(name="ACCOUNT")
	private String tradingAccount;
	
	@Column(name="IDENTIFIER")
	private String securityIdentifier;
	
	@Column(name="QUANTITY")
	private long quantity;
	
	protected Event() {}

	public Event(String eventId, String eventType, String tradingAccount, String securityIdentifier,
			long quantity) {
		this.eventId = eventId;
		this.eventType = eventType;
		this.tradingAccount = tradingAccount;
		this.securityIdentifier = securityIdentifier;
		this.quantity = quantity;
	}

	public String getEventId() {
		return eventId;
	}

	public String getEventType() {
		return eventType;
	}

	public String getTradingAccount() {
		return tradingAccount;
	}

	public String getSecurityIdentifier() {
		return securityIdentifier;
	}

	public long getQuantity() {
		return quantity;
	}
}
