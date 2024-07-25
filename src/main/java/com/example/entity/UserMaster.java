package com.example.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "USER_MASTER")
public class UserMaster {
	
	@Id
	@GeneratedValue(strategy  = GenerationType.IDENTITY)
	private Integer userID;
	
	private String fullName;
	
	private String email;
	
	private Long mobile;
	
	private String gender;
	
	private LocalDate date;
	
	private Long ssn;
	
	private String password;
	
	private String accStatus;
	
	private LocalDate createDate;
	
	private LocalDate updateDate;
	
	private String createdBy;
	
	private String updatedBy;

}
