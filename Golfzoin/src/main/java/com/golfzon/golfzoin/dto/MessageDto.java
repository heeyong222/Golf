package com.golfzon.golfzoin.dto;

import lombok.Data;

@Data
public class MessageDto {

    private String data;
    private String author;
    private String date;
    private String roomNo;
    private String profile;
    public MessageDto(String roomNo, String data, String author, String date, String profile) {
        this.data = data;
        this.author = author;
        this.date = date;
        this.roomNo = roomNo;
        this.profile = profile;
    }

    public MessageDto() {
    }
    
    public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}

	public String getData() {
        return data;
    }
    
    public String getRoomNo() {
		return roomNo;
	}

	public void setRoomNo(String roomNo) {
		this.roomNo = roomNo;
	}

	public void setData(String data) {
        this.data = data;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    
    
    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	@Override
    public String toString() {
        return "{\"author\":"+"\""+author+"\"" + ",\"data\":"+"\""+data+"\"" +  ",\"roomNo\":"+"\""+roomNo+"\"" +",\"date\":"+"\""+date+"\"}";
    }
}