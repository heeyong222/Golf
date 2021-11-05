package com.golfzon.golfzoin.dto;

public class ChatRoomDto {
	private String roomNo;// 조인 방 생성자
	private String title;
	public String getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public static ChatRoomDto create(String title, String roomNo) {
		System.out.println("dto");
		ChatRoomDto chatRoomDto = new ChatRoomDto();
		chatRoomDto.roomNo = roomNo;
		chatRoomDto.title = title;
		return chatRoomDto;
	}
	
}
