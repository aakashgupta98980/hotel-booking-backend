package com.altir.HotelManager.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;


@Entity
@Table(name = "dates")
public class Dates {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "booked_on")
	@CreationTimestamp
	private Date bookedOn;

	@Column(name = "booking_date")
	private Date bookingDate;
	
	public Dates() {
		super();
	}

	public Dates(Date bookedOn, Date bookingDate) {
		super();
		this.bookedOn = bookedOn;
		this.bookingDate = bookingDate;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getBookedOn() {
		return bookedOn;
	}

	public void setBookedOn(Date bookedOn) {
		this.bookedOn = bookedOn;
	}

	public Date getBookingDate() {
		return bookingDate;
	}

	public void setBookingDate(Date bookingDate) {
		this.bookingDate = bookingDate;
	}
}
