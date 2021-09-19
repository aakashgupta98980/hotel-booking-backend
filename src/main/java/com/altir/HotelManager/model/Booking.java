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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name = "bookings")
public class Booking {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "booking_id")
	private long bookingId;

	@ManyToMany
	private List<Room> rooms = new ArrayList<>(); 

	@OneToOne(cascade = CascadeType.ALL)
	private User user;
	
	@Column(name = "total_booking_price")
	private double bookingPrice;

	public Booking(List<Room> rooms, List<Dates> dates, User user, double bookingPrice) {
		super();
		this.rooms = rooms;
		this.user = user;
		this.bookingPrice = bookingPrice;
	}

	public Booking() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getBookingId() {
		return bookingId;
	}

	public void setBookingId(long bookingId) {
		this.bookingId = bookingId;
	}

	public List<Room> getRooms() {
		return rooms;
	}

	public void setRooms(List<Room> rooms) {
		this.rooms = rooms;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public double getBookingPrice() {
		return bookingPrice;
	}

	public void setBookingPrice(double bookingPrice) {
		this.bookingPrice = bookingPrice;
	}	
}
