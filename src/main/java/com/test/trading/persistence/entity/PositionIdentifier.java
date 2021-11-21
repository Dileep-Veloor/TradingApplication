package com.test.trading.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PositionIdentifier implements Serializable{

	@Column(name="ACCOUNT")
	private String tradingAccount;
	
	@Column(name ="IDENTIFIER")
	private String securityIdentifier;

	protected PositionIdentifier() {}
	
	public PositionIdentifier(String tradingAccount, String securityIdentifier) {
		super();
		this.tradingAccount = tradingAccount;
		this.securityIdentifier = securityIdentifier;
	}

	public String getTradingAccount() {
		return tradingAccount;
	}

	public String getSecurityIdentifier() {
		return securityIdentifier;
	}
	
}
