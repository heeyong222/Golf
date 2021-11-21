package com.golfzon.golfzoin.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.golfzon.golfzoin.dto.MessageDto;

@Mapper
public interface ChatMapper {
	
	// 채팅방 입장 시 기존 채팅 내역 조회
	public List<MessageDto> chatList(int roomNo) throws SQLException;
	// 채팅 rdb에 저장
	public int pushchat(Map<String, String> map) throws SQLException;
}
