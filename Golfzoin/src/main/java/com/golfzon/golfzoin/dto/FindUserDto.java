package com.golfzon.golfzoin.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FindUserDto {
	private String id;
	private String profile;
	private String nickname;
	private int isfollow;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProfile() {
		return profile;
	}
	public void setProfile(String profile) {
		this.profile = profile;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public int getIsfollow() {
		return isfollow;
	}
	public void setIsfollow(int isfollow) {
		this.isfollow = isfollow;
	}
	@Override
	public String toString() {
		return "FindUserDto [id=" + id + ", profile=" + profile + ", nickname=" + nickname + ", isfollow=" + isfollow
				+ "]";
	}
	
	
}
