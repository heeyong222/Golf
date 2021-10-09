package com.golfzon.golfzoin.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinOffDto {
	private int roomNo;
	private String type;
	private String hostid;
	private String date;
	private String time;
	private int nowcount;
	private int totalcount;
	private String place;
	private double lat;
	private double lon;
	private String title;
	private String body;
	private String thumbnail;
	private List<JoinMemberDto> members = new ArrayList<JoinMemberDto>();
	
	public List<JoinMemberDto> getMembers() {
		return members;
	}
	public void setMembers(List<JoinMemberDto> members) {
		this.members = members;
	}
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	public int getRoomNo() {
		return roomNo;
	}
	public void setRoomNo(int roomNo) {
		this.roomNo = roomNo;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getHostid() {
		return hostid;
	}
	public void setHostid(String hostid) {
		this.hostid = hostid;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public int getNowcount() {
		return nowcount;
	}
	public void setNowcount(int nowcount) {
		this.nowcount = nowcount;
	}
	public int getTotalcount() {
		return totalcount;
	}
	public void setTotlacount(int totlacount) {
		this.totalcount = totlacount;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public double getLat() {
		return lat;
	}
	public void setLat(double lat) {
		this.lat = lat;
	}
	public double getLon() {
		return lon;
	}
	public void setLon(double lon) {
		this.lon = lon;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getThumbnail() {
		return thumbnail;
	}
	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	
	
	
	
	
}
