package com.golfzon.golfzoin.service;

import java.sql.SQLException;
import java.util.Map;

import com.golfzon.golfzoin.dto.UserDto;

public interface AuthService {
	// signup
	public boolean userRegister(UserDto userDto) throws SQLException;
	
	// login
	public UserDto login(Map<String, String> map) throws SQLException;
	
	// check id
	public int isvalidid(String id) throws SQLException;
		
	// check nickname
	public int isvalidnickname(String nickname) throws SQLException;
}
