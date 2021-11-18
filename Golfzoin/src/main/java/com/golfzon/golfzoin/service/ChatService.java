package com.golfzon.golfzoin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.golfzon.golfzoin.dto.MessageDto;

public interface ChatService {
	// 채팅방 입장 시 기존 채팅 내역 조회
	public List<MessageDto> chatList(int roomNo) throws SQLException;
	// 채팅 rdb에 저장
	public boolean pushchat(Map<String, String> map) throws SQLException;
}
