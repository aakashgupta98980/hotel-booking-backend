package com.altir.HotelManager.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;


@Entity
@Table(name = "rooms")
public class Room {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "room_id")
	private long roomId;
	
	@Column(name = "room_type")
	private String roomType;
	
	@Column(name = "room_price")
	private double roomPrice;
	
	@Column(name = "is_booked")
	private boolean isBooked;
	
	@ManyToMany(cascade = CascadeType.ALL)
	private List<Dates> dates = new ArrayList<>();
	
	public Room() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Room(String roomType, double roomPrice, boolean isBooked, List<Dates> dates, Booking booking) {
		super();
		this.roomType = roomType;
		this.roomPrice = roomPrice;
		this.isBooked = isBooked;
		this.dates = dates;
	}

	public long getRoomId() {
		return roomId;
	}

	public void setRoomId(long roomId) {
		this.roomId = roomId;
	}

	public String getRoomType() {
		return roomType;
	}

	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public double getRoomPrice() {
		return roomPrice;
	}

	public void setRoomPrice(double roomPrice) {
		this.roomPrice = roomPrice;
	}

	public boolean isBooked() {
		return isBooked;
	}

	public void setBooked(boolean isBooked) {
		this.isBooked = isBooked;
	}

	public List<Dates> getDates() {
		return dates;
	}

	public void setDates(List<Dates> dates) {
		this.dates = dates;
	}
}
