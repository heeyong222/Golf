package com.golfzon.golfzoin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private String authType;
	private String id;
	private String pw;
	private String email;
	private String name;
	private String nickname;
	private String address;
	private String birth;
	private String p_number;
	private String gender;
	private int hit;
	private double lat;
	private double lon;
	private String profile;
	
	
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getBirth() {
		return birth;
	}
	public void setBirth(String birth) {
		this.birth = birth;
	}
	public String getP_number() {
		return p_number;
	}
	public void setP_number(String p_number) {
		this.p_number = p_number;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public int getHit() {
		return hit;
	}
	public void setHit(int hit) {
		this.hit = hit;
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
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public UserDto(String authType, String id, String pw, String email, String name, String nickname, String address,
			String birth, String p_number, String gender, int hit, float lat, float lon, String profile) {
		super();
		this.authType = authType;
		this.id = id;
		this.pw = pw;
		this.email = email;
		this.name = name;
		this.nickname = nickname;
		this.address = address;
		this.birth = birth;
		this.p_number = p_number;
		this.gender = gender;
		this.hit = hit;
		this.lat = lat;
		this.lon = lon;
		this.profile = profile;
	}
	@Override
	public String toString() {
		return "UserDto [authType=" + authType + ", id=" + id + ", pw=" + pw + ", email=" + email + ", name=" + name
				+ ", nickname=" + nickname + ", address=" + address + ", birth=" + birth + ", p_number=" + p_number
				+ ", gender=" + gender + ", hit=" + hit + ", lat=" + lat + ", lon=" + lon + ", profile=" + profile
				+ "]";
	}
	
}
