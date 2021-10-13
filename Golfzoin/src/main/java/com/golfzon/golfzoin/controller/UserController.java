package com.golfzon.golfzoin.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.golfzon.golfzoin.dto.FindUserDto;
import com.golfzon.golfzoin.dto.UserDetailDto;
import com.golfzon.golfzoin.dto.UserDto;
import com.golfzon.golfzoin.service.UserService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserController {
	
	@Autowired
	UserService userService;
	
	
	// 유저 상세 정보 조회
	@GetMapping(value="detail/{userid}")
	public ResponseEntity<UserDetailDto> userdetail(@PathVariable String userid){
		try {
			UserDetailDto userDetailDto = userService.userdetail(userid);
			
			return new ResponseEntity<UserDetailDto>(userDetailDto, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 회원 정보 수정
	@PatchMapping(value="/modifyuser")
	public ResponseEntity<String> modifyuser(@RequestBody UserDto userDto){
		try {
			System.out.println(userDto.toString());
			if(userService.modifyuser(userDto)) 
				return new ResponseEntity("SUCCESS", HttpStatus.OK);
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	// 사용자 검색
	@GetMapping(value="finduser")
	public ResponseEntity<List<FindUserDto>> finduser(@RequestParam String keyword, @RequestParam String userid){
		try {
			List<FindUserDto> list = userService.finduser(keyword, userid);
			return new ResponseEntity<List<FindUserDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 팔로우 추가
	@PostMapping(value="addfollow")
	public ResponseEntity<String> addfollow(@RequestBody Map<String, String> map){
		try {
			if(userService.addfollow(map)) 
				return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			return new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 팔로우 조회 - 나를 팔로우 한 사람들
	@GetMapping(value="follower")
	public ResponseEntity<List<FindUserDto>> followerlist(@RequestParam String id){
		try {
			List<FindUserDto> list = userService.followerlist(id);
			return new ResponseEntity<List<FindUserDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	
	// 팔로잉 조회 - 내가 팔로우 한 사람들
	@GetMapping(value="following")
	public ResponseEntity<List<FindUserDto>> followinglist(@RequestParam String id){
		try {
			List<FindUserDto> list = userService.followinglist(id);
			return new ResponseEntity<List<FindUserDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
}
