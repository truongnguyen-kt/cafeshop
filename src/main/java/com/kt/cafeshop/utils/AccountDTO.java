package com.kt.cafeshop.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AccountDTO {
	private String username;
	
	private String password;
	
	private String role;
}
