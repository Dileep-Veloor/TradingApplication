package com.test.trading.persistence.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

@Entity
@Table(name="T_POSITION")
public class Position {
	
	@EmbeddedId
	private PositionIdentifier positionId;
	
	@Column(name="TOTAL_QUANTITY")
	private long totalQuantity;
	
	@Column(name="VERSION")
	@Version
	private int version=0;
	
	@Transient
	private List<Event> eventList;

	protected Position() {}
	
	public Position(PositionIdentifier positionId, long totalQuantity) {
		this.positionId = positionId;
		this.totalQuantity = totalQuantity;
	}

	public PositionIdentifier getPositionId() {
		return positionId;
	}

	public long getTotalQuantity() {
		return totalQuantity;
	}

	public void setTotalQuantity(long totalQuantity) {
		this.totalQuantity = totalQuantity;
	}

	public int getVersion() {
		return version;
	}
	
	public List<Event> getEventList() {
		return eventList;
	}

	public void setEventList(List<Event> eventList) {
		this.eventList = eventList;
	}
	
	
}
