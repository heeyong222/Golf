package com.golfzon.golfzoin.controller;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.golfzon.golfzoin.dto.UserDto;
import com.golfzon.golfzoin.service.AuthService;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	// signup
	@PostMapping(value="/signup")
	public ResponseEntity signup(@RequestBody UserDto userDto){
		System.out.println(userDto.getId());
		
		try {
			System.out.println(userDto.toString());
			if(authService.userRegister(userDto)){
				return new ResponseEntity(HttpStatus.OK);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return new ResponseEntity(HttpStatus.NO_CONTENT);
	}
	
	// login
	@PostMapping(value="/signin")
	public ResponseEntity<UserDto> login(@RequestBody Map<String,String> map) {
		try {
			UserDto userDto = authService.login(map);
			//세션? 토큰? 정하기
			
//			session.setAttribute("userinfo", userDto);
			
			
			if(userDto == null) {
				return new ResponseEntity("FAIL", HttpStatus.NO_CONTENT);
			}
			if(userDto != null) {
//				session.setAttribute("userinfo", userDto);
				return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	// logout
	@GetMapping(value="/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}
	
	// ID 중복 체크
	@GetMapping(value="/id/{id}")
	public ResponseEntity isvalidid(@PathVariable("id") String id) {
		int idcnt = 0;
		try {
			idcnt = authService.isvalidid(id); // 해당하는 id의 유저 정보수
		} catch (SQLException e) {
			e.printStackTrace();
		}
		boolean isId = false;
		if(idcnt == 0) isId = true; // 해당하는 id의 유저가 없다면 가능
		return new ResponseEntity<Boolean>(isId, HttpStatus.OK);
		
	}
	
	// 닉네임 중복 체크
	@GetMapping(value="/nickname/{nickname}")
	public ResponseEntity isvalidnickname(@PathVariable("nickname") String nickname) {
		int nicknamecnt = 0;
		try {
			nicknamecnt = authService.isvalidnickname(nickname);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		boolean isNickname = false;
		if(nicknamecnt == 0) isNickname = true;
		
		return new ResponseEntity<Boolean>(isNickname, HttpStatus.OK);
	}
	
}
