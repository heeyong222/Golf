package com.golfzon.golfzoin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.golfzon.golfzoin.dto.FindUserDto;
import com.golfzon.golfzoin.dto.UserDetailDto;
import com.golfzon.golfzoin.dto.UserDto;

public interface UserService {
	// 특정 사용자 상세 정보 조회
	public UserDetailDto userdetail(String userid) throws SQLException;
	// 유저 정보 수정
	public boolean modifyuser(UserDto userDto) throws SQLException;
	// 사용자 검색
	public List<FindUserDto> finduser(String keyword, String userid) throws SQLException;
	
	// 팔로우 추가
	public boolean addfollow(Map<String, String> map) throws SQLException;
	
	// 팔로워 조회
	public List<FindUserDto> followerlist(String id) throws SQLException;
	// 팔로잉 조회
	public List<FindUserDto> followinglist(String id) throws SQLException;
}
