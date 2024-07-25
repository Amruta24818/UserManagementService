package com.example.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.entity.UserMaster;

public interface UserMasteRepo extends JpaRepository<UserMaster, Integer> {

	public UserMaster findByEmailAndPassword(String Email, String Password);
	
	public UserMaster findByEmail(String Email);
}
