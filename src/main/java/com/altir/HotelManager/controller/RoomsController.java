package com.altir.HotelManager.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.altir.HotelManager.exception.ResourceNotFoundException;
import com.altir.HotelManager.model.Booking;
import com.altir.HotelManager.model.Room;
import com.altir.HotelManager.repository.BookingRepository;
import com.altir.HotelManager.repository.RoomRepository;

@RestController
@RequestMapping("/api/v1/")
public class RoomsController {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private BookingRepository bookingRepository;
	
	//GET ALL ROOMS
	@GetMapping("/rooms")
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}
	
	//GET ALL AVAILABLE ROOMS
	@GetMapping("/rooms/available")
	public List<Room> getAvailableRooms() {
		List<Room> allRooms = roomRepository.findAll();
		
		List<Room> availableRooms = allRooms.stream().filter(room -> room.isBooked() == false).collect(Collectors.toList());
		
		return availableRooms;
	}
	
	//GET ALL AVAILABLE ROOMS
	@GetMapping("/rooms/booked")
	public List<Room> getBookedRooms() {
		List<Room> allRooms = roomRepository.findAll();
		
		List<Room> bookedRooms = allRooms.stream().filter(room -> room.isBooked() == true).collect(Collectors.toList());
		
		return bookedRooms;
	}
	
	//GET REVENUE
	@GetMapping("/rooms/revenue")
	public Double getRevenue() throws Exception {
		List<Booking> bookings = bookingRepository.findAll();
		Double revenue = 0.0;
		
		try {
		for(int i=0; i<bookings.size(); i++) {
			Booking booking = bookings.get(i);
			List<Room> rooms = booking.getRooms();
			
			for(int j=0; j<rooms.size(); j++) {
				Room room = rooms.get(j);
				int numberOfDaysBooked = room.getDates().size();
				revenue += room.getRoomPrice()*numberOfDaysBooked;
			}
		}
		
		} catch (Exception e) {
			throw new Exception("Unable to fetch revenue. " + e);
		}
		
		return revenue;
	}
	
	//ADD ROOM
	@PostMapping("/rooms")
	public Room addRoom(@RequestBody Room room) {
		return roomRepository.save(room);
	}
	
	//GET ROOM BY ID
	@GetMapping("/rooms/{id}")
	public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
		Room room = roomRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Room doesn't exist with id: " + id));
		return ResponseEntity.ok(room);
	}
	
	//UPDATE ROOM
	@PutMapping("/rooms/{id}")
	public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room roomDetails) {
		Room room = roomRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Room doesn't exist with meeting id: " + id));
		
		room.setRoomType(roomDetails.getRoomType());
		room.setRoomPrice(roomDetails.getRoomPrice());
		
		Room updatedRoom = roomRepository.save(room);
		return ResponseEntity.ok(updatedRoom);
	}
	
//	DELETE ROOM
	@DeleteMapping("/rooms/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteRoom(@PathVariable Long id) {
		Room room = roomRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Room doesn't exist with id: " + id));
		
		roomRepository.delete(room);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("Deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
}
