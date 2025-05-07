package com.kt.cafeshop.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "account")
@Data
public class Account {
	@Id
	private String username;
	
	private String password;
	
	private String role;
}
