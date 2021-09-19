package com.altir.HotelManager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.altir.HotelManager.model.User;

@Repository
public interface UserRepositiory extends JpaRepository<User, Long> {

}
