package com.golfzon.golfzoin.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.golfzon.golfzoin.dto.UserDto;

@Mapper
public interface AuthMapper {
	// signup
	public int userRegister(UserDto userDto) throws SQLException;
	
	// login
	public UserDto login(Map<String, String> map) throws SQLException;
	
	// check id
	public int isvalidid(String id) throws SQLException;
	
	// check nickname
	public int isvalidnickname(String nickname) throws SQLException;
}
