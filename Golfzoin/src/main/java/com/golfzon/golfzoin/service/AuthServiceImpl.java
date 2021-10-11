package com.golfzon.golfzoin.service;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golfzon.golfzoin.dto.UserDto;
import com.golfzon.golfzoin.mapper.AuthMapper;
@Service
public class AuthServiceImpl implements AuthService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public boolean userRegister(UserDto userDto) throws SQLException {
		return sqlSession.getMapper(AuthMapper.class).userRegister(userDto) == 1;
	}

	@Override
	public UserDto login(Map<String, String> map) throws SQLException {
		if(map.get("id") == null || map.get("pw") == null) return null;
		
		return sqlSession.getMapper(AuthMapper.class).login(map);
	}

	@Override
	public int isvalidid(String id) throws SQLException {
		return sqlSession.getMapper(AuthMapper.class).isvalidid(id);
	}

	@Override
	public int isvalidnickname(String nickname) throws SQLException {
		return sqlSession.getMapper(AuthMapper.class).isvalidnickname(nickname);
	}
	
	
	
}
