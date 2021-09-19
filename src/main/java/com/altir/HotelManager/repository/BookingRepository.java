package com.altir.HotelManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altir.HotelManager.model.Booking;

public interface BookingRepository extends JpaRepository<Booking, Long> {

}
