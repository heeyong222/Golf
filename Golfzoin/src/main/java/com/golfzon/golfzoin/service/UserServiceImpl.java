package com.golfzon.golfzoin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.golfzon.golfzoin.dto.FindUserDto;
import com.golfzon.golfzoin.dto.UserDetailDto;
import com.golfzon.golfzoin.dto.UserDto;
import com.golfzon.golfzoin.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public UserDetailDto userdetail(String userid) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).userdetail(userid);
	}

	@Override
	public boolean modifyuser(UserDto userDto) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).modifyuser(userDto) == 1;
	}

	@Override
	public List<FindUserDto> finduser(String keyword, String userid) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).finduser(keyword, userid);
	}

	@Override
	public boolean addfollow(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).addfollow(map) == 1;
	}

	@Override
	public List<FindUserDto> followerlist(String id) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).followerlist(id);
	}

	@Override
	public List<FindUserDto> followinglist(String id) throws SQLException {
		return sqlSession.getMapper(UserMapper.class).followinglist(id);
	}

}
