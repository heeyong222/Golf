package com.golfzon.golfzoin.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JoinDetailDto {
	private int roomNo;
	private String type;
	private String hostid;
	private String date;
	private String time;
	private int nowcount;
	private int totalcount;
	private String place;
	private float lat;
	private float lon;
	private String pw;
	private String title;
	private String body;
	private String thumbnail;
	private int isapply;
	private int isaccept;
	private List<JoinMemberDto> members = new ArrayList<JoinMemberDto>();
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
	public void setTotalcount(int totalcount) {
		this.totalcount = totalcount;
	}
	public String getPlace() {
		return place;
	}
	public void setPlace(String place) {
		this.place = place;
	}
	public float getLat() {
		return lat;
	}
	public void setLat(float lat) {
		this.lat = lat;
	}
	public float getLon() {
		return lon;
	}
	public void setLon(float lon) {
		this.lon = lon;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
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
	public List<JoinMemberDto> getMembers() {
		return members;
	}
	public void setMembers(List<JoinMemberDto> members) {
		this.members = members;
	}
	public int getIsapply() {
		return isapply;
	}
	public void setIsapply(int isapply) {
		this.isapply = isapply;
	}
	public int getIsaccept() {
		return isaccept;
	}
	public void setIsaccept(int isaccept) {
		this.isaccept = isaccept;
	}
	@Override
	public String toString() {
		return "JoinDetailDto [roomNo=" + roomNo + ", type=" + type + ", hostid=" + hostid + ", date=" + date
				+ ", time=" + time + ", nowcount=" + nowcount + ", totalcount=" + totalcount + ", place=" + place
				+ ", lat=" + lat + ", lon=" + lon + ", pw=" + pw + ", title=" + title + ", body=" + body
				+ ", thumbnail=" + thumbnail + ", isapply=" + isapply + ", isaccept=" + isaccept + ", members="
				+ members + "]";
	}
	
	
}
