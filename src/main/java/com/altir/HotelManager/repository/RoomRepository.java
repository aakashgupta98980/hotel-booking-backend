package com.altir.HotelManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.altir.HotelManager.model.Room;

public interface RoomRepository extends JpaRepository<Room, Long>{

}
