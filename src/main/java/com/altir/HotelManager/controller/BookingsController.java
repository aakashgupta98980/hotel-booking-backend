package com.altir.HotelManager.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.altir.HotelManager.exception.ResourceAlreadyUsedException;
import com.altir.HotelManager.exception.ResourceNotFoundException;
import com.altir.HotelManager.model.Booking;
import com.altir.HotelManager.model.Dates;
import com.altir.HotelManager.model.Room;
import com.altir.HotelManager.repository.BookingRepository;
import com.altir.HotelManager.repository.RoomRepository;
import com.altir.HotelManager.service.BookingService;
import com.altir.HotelManager.service.RoomService;

@RestController
@RequestMapping("/api/v1/")
public class BookingsController {
	

	private final BookingService bookingService;
	private final RoomService roomService;
	
	@Autowired
	public BookingsController(BookingService bookingService, RoomService roomService) {
		this.bookingService = bookingService;
		this.roomService = roomService;
	}

	@Autowired
	private BookingRepository bookingRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	//GET ALL BOOKINGS
	@GetMapping("/bookings")
	public List<Booking> getAllBookings() {
		return bookingRepository.findAll();
	}
	
	//GET ALL BOOKINGS
	@GetMapping("/bookings/rooms")
	public List<Room> getAllRooms() {
		return roomRepository.findAll();
	}
	
	//CREATE BOOKING
	@PostMapping(value = "/bookings", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Booking createBooking(@RequestBody Booking booking) throws Exception {
		
		List<Room> rooms = new ArrayList<>();
		
		try {
		for(int r=0; r<booking.getRooms().size(); r++) {
			
		long roomId = booking.getRooms().get(r).getRoomId();
		List<Dates> bookingDates = booking.getRooms().get(r).getDates();
		
		roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room doesn't exist with id: " + roomId));
		
		if(roomRepository.getById(roomId).getDates().size() != 0) 
		{
			for(int i=0; i<bookingDates.size(); i++) {
				Date bookingDate = bookingDates.get(i).getBookingDate();
				
				for(int j=0; j<roomRepository.getById(roomId).getDates().size(); j++) {
			if(bookingDate.compareTo(roomRepository.getById(roomId).getDates().get(j).getBookingDate()) == 0 &&  roomRepository.getById(roomId).isBooked())
				throw new ResourceAlreadyUsedException("Room " + roomId + " already booked for date: " + bookingDate);
				}
			}
		}
			Room room = roomRepository.findById(roomId)
					.orElseThrow(() -> new ResourceNotFoundException("Room doesn't exist with id: " + roomId));
			room.setBooked(true);
			room.setDates(bookingDates);
			room.setRoomPrice(room.getRoomPrice());
			room.setRoomType(room.getRoomType());
			rooms.add(room);
			booking.getRooms().set(r, room);
			booking.setBookingPrice(booking.getBookingPrice() + room.getRoomPrice()*bookingDates.size());
		}
		}catch (Exception e) {
			throw new Exception("Unable to book the room with given roomId/dates. Please try with some other roomId/dates. " + e);
		}
		
		rooms.forEach((room) -> roomRepository.save(room));
		
		return bookingRepository.save(booking);
	}
	
	//GET BOOKING BY ID
	@GetMapping("/bookings/{id}")
	public ResponseEntity<Booking> getBookingById(@PathVariable Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking doesn't exist with id: " + id));
		return ResponseEntity.ok(booking);
	}
	
//	DELETE BOOKING
	@DeleteMapping("/bookings/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteBooking(@PathVariable Long id) {
		Booking booking = bookingRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Booking doesn't exist with id: " + id));
		
		bookingRepository.delete(booking);
		Map<String, Boolean> response = new HashMap<String, Boolean>();
		response.put("Deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}

	public BookingRepository getBookingRepository() {
		return bookingRepository;
	}

	public void setBookingRepository(BookingRepository bookingRepository) {
		this.bookingRepository = bookingRepository;
	}

	public RoomRepository getRoomRepository() {
		return roomRepository;
	}

	public void setRoomRepository(RoomRepository roomRepository) {
		this.roomRepository = roomRepository;
	}

	public BookingService getBookingService() {
		return bookingService;
	}

	public RoomService getRoomService() {
		return roomService;
	}
}
