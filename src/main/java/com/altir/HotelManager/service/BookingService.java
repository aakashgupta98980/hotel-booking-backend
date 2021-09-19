package com.altir.HotelManager.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.altir.HotelManager.repository.BookingRepository;

@Service
public class BookingService {

	Date date;
	BookingRepository bookingRepository;
	
	public void updateBooking(Date date) {
		
		if(date.getDate() == date.getDate()) {
//			bookingRepository.get
		}
	}
}
