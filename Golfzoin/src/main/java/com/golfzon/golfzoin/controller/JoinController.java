package com.golfzon.golfzoin.controller;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.golfzon.golfzoin.dto.CalendarDto;
import com.golfzon.golfzoin.dto.HostAlarmDto;
import com.golfzon.golfzoin.dto.JoinDetailDto;
import com.golfzon.golfzoin.dto.JoinLogDto;
import com.golfzon.golfzoin.dto.JoinMemberDto;
import com.golfzon.golfzoin.dto.JoinOffDto;
import com.golfzon.golfzoin.dto.JoinOnDto;
import com.golfzon.golfzoin.dto.JoinTempUserDto;
import com.golfzon.golfzoin.dto.MainJoinDto;
import com.golfzon.golfzoin.dto.UserDto;
import com.golfzon.golfzoin.repository.ChatRoomRepo;
import com.golfzon.golfzoin.service.JoinService;
import com.golfzon.golfzoin.service.MessageSubscriber;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

@RestController
@RequestMapping("/api/join")
@CrossOrigin("*")
public class JoinController {
	@Autowired
	private JoinService joinService;
	@Autowired
	private RedisMessageListenerContainer redisMessageListener;
	@Autowired
	private MessageSubscriber messageSubscriber;
	@Autowired
	private ChatRoomRepo chatRoomRepo;
	// 조인 모집 등록
	@PostMapping(value="/registjoin")
	public ResponseEntity<String> registjoin(@RequestBody Map<String,String> map) {
		System.out.println(">>> registjoin map : "+ map.toString());
		try {
//			ChannelTopic channel = new ChannelTopic(map.get("roomNo"));
//			redisMessageListener.addMessageListener(messageSubscriber, channel);
//			chatRoomRepo.createChatRoom(map.get("title"), map.get("roomNo"));
			if(map.get("pw") != null) {// pw가 null이 아닌 경우 online 조인 모집
				if(joinService.registonjoin(map)) {
					// 정상 등록 된 경우 hostid로 생성된 가장 최근 roomId를 가져와 joinlist user테이블에도 등록
					joinService.registhost(map);
					
					// 채팅 창 리스너 등록 필요

					return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
				}
			}else { // pw 가 null 인 경우 offline 조인 모집
				if(joinService.registoffjoin(map)) {
					// 정상 등록 된 경우 hostid로 생성된 가장 최근 roomId를 가져와 joinlist user테이블에도 등록
					joinService.registhost(map);
					
					// 채팅 창 리스너 등록 필요

					return new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<String>("FAIL", HttpStatus.BAD_REQUEST);
	}
	
	// 주변 오프라인 모집 조회
	@PostMapping(value="/offlinejoinlist")
	public ResponseEntity<JSONArray> nearofflist(@RequestBody Map<String, String> map) {
		System.out.println(">>> offlinejoinlist "+map.toString());
		try {
			if(map.get("count") == null && map.get("date") == null) {
				
			}
			System.out.println(map.get("##date"));
			System.out.println();
			List<JoinOffDto> list = joinService.nearofflist(map);
			for(JoinOffDto j : list) {
				System.out.println(">> "+j.toString());
			}
//			System.out.println("########"+ list.get(0).getLat());
			JSONArray jsonArray = new JSONArray();
			for(JoinOffDto joinOffDto : list) {
				List<JoinMemberDto> members = joinService.joinmembers(joinOffDto.getRoomNo());
				joinOffDto.setMembers(members);
				JSONObject jsonobject = new JSONObject();
				// roomNo, type, hostid, date, time, nowcount, totalcount, place, lat, lon, title, body, thumbnail
				jsonobject.put("roomNo", joinOffDto.getRoomNo());
				jsonobject.put("type", joinOffDto.getType());
				jsonobject.put("date", joinOffDto.getDate());
				jsonobject.put("time", joinOffDto.getTime());
				jsonobject.put("nowcount", joinOffDto.getNowcount());
				jsonobject.put("totalcount", joinOffDto.getTotalcount());
				jsonobject.put("place", joinOffDto.getPlace());
				jsonobject.put("lat", joinOffDto.getLat());
				jsonobject.put("lon", joinOffDto.getLon());
				jsonobject.put("title", joinOffDto.getTitle());
				jsonobject.put("body", joinOffDto.getBody());
				jsonobject.put("thumbnail", joinOffDto.getThumbnail());
				JSONArray jarray = new JSONArray();
				int totalhit = 0;
				int totalmember = 0;
				for(JoinMemberDto jmd : members) {
					
					JSONObject jobject = new JSONObject();
					jobject.put("nickname", jmd.getNickname());
					jobject.put("age", jmd.getAge());
					jobject.put("hit", jmd.getHit());
					jobject.put("profile", jmd.getProfile());
					jarray.add(jobject);
				}
				jsonobject.put("members", jarray);
				// 주변 조인 평균 hit로 필터링
				if(map.get("hit") != null && totalmember != 0) {
					if(Integer.parseInt(map.get("hit")) >= totalhit / totalmember) {
						jsonArray.add(jsonobject);
					}
				}else {
					jsonArray.add(jsonobject);
				}
//				System.out.println(totalhit / totalmember);
			}
//			System.out.println(jsonArray.toString());
//			Gson gson = new Gson();
//			String result = gson.toJson(list);
//			System.out.println(result);
			return new ResponseEntity<JSONArray>(jsonArray, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL",HttpStatus.BAD_REQUEST);
		}
	}
	// 온라인 리스트
	@PostMapping(value="/onlinejoinlist")
	public ResponseEntity<JSONArray> onjoinlist(@RequestBody Map<String, String> map) {
		System.out.println(">>> onlinejoinlist");
		try {
			
			List<JoinOnDto> list = joinService.onlist(map);
			JSONArray jsonArray = new JSONArray();
			System.out.println("#####");
			System.out.println("###"+list.toString());
			
			for(JoinOnDto joinOnDto : list) {
				List<JoinMemberDto> members = joinService.joinmembers(joinOnDto.getRoomNo());
				joinOnDto.setMembers(members);
				JSONObject jsonobject = new JSONObject();
				// roomNo, type, hostid, date, time, nowcount, totalcount, pw, title, body, thumbnail
				jsonobject.put("roomNo", joinOnDto.getRoomNo());
				jsonobject.put("type", joinOnDto.getType());
				jsonobject.put("date", joinOnDto.getDate());
				jsonobject.put("time", joinOnDto.getTime());
				jsonobject.put("nowcount", joinOnDto.getNowcount());
				jsonobject.put("totalcount", joinOnDto.getTotalcount());
				jsonobject.put("pw", joinOnDto.getPw());
				jsonobject.put("title", joinOnDto.getTitle());
				jsonobject.put("body", joinOnDto.getBody());
				jsonobject.put("thumbnail", joinOnDto.getThumbnail());
				JSONArray jarray = new JSONArray();
				for(JoinMemberDto jmd : members) {
					JSONObject jobject = new JSONObject();
					jobject.put("nickname", jmd.getNickname());
					jobject.put("age", jmd.getAge());
					jobject.put("hit", jmd.getHit());
					jobject.put("profile", jmd.getProfile());
					jarray.add(jobject);
				}
				jsonobject.put("members", jarray);
				jsonArray.add(jsonobject);
			}
			return new ResponseEntity<JSONArray>(jsonArray, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL",HttpStatus.BAD_REQUEST);
		}
		
	}
	// 조인 신청
	@PostMapping(value="/applyjoin")
	public ResponseEntity<String> applyjoin(@RequestBody Map<String,String> map) {
		System.out.println(">>> apply join roomNo : "+map.get("roomNo")+" id : "+map.get("userid"));
		try {
			joinService.applyjoin(map);
			return new ResponseEntity("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL",HttpStatus.BAD_REQUEST);
		}
	}
	// 호스트용 알람 (수락 대기 인원 조회)
	@GetMapping(value="/jointempuser/{userid}")
	public ResponseEntity<List<HostAlarmDto>> jointempuser(@PathVariable String userid) {
		System.out.println(">>> jointempuser");
		try {
			return new ResponseEntity<List<HostAlarmDto>>(joinService.jointempuser(userid), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL",HttpStatus.BAD_REQUEST);
		}
	}
	
	// 호스트가 신청 수락
	@PostMapping(value="/acceptuser")
	public ResponseEntity<String> acceptuser(@RequestBody Map<String, String> map){
		System.out.println(">>> acceptuser : "+ map.toString());
		try {
			joinService.acceptuser(map);
			joinService.insertjoinlog(map);
			joinService.removetempuser(map);
			return new ResponseEntity("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL",HttpStatus.BAD_REQUEST);
		}
	}
	
	// 조인 신청 거절
	@PostMapping(value="/refuseuser")
	public ResponseEntity<String> refuseuser(@RequestBody Map<String, String> map){
		System.out.println(">>> refuseuser");
		try {
			joinService.insertjoinlog(map);
			joinService.refuseuser(map);
			return new ResponseEntity("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL",HttpStatus.BAD_REQUEST);
		}
	}
		
	// 조인 모집 취소
	@DeleteMapping(value="/canceljoin/{roomNo}")
	public ResponseEntity<String> canceljoin(@PathVariable String roomNo) {
		System.out.println(">>> cancel join");
		try {
			joinService.canceljoin(roomNo); // 등록된 조인 모집 방 삭제
			joinService.cancelmember(roomNo); // 해당 방에 참여중이었던 멤버 삭제
			return new ResponseEntity("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	// 신청한 사용자 알람
	@GetMapping(value="/alarm/{userid}")
	public ResponseEntity<List<JoinLogDto>> showlog(@PathVariable String userid){
		System.out.println(">>> alarm for user");
		try {
			List<JoinLogDto> list = joinService.showlog(userid);
//			System.out.println(list.get(0).getTitle());
			
			return new ResponseEntity<List<JoinLogDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL",HttpStatus.BAD_REQUEST);
		}
	}
	// 신청한 사용자 알람 삭제
	@DeleteMapping(value="/alarm/delete")
	public ResponseEntity<String> deletealarm(@RequestBody Map<String, String> map){
		System.out.println(">>> delete alarm");
		try {
			joinService.deletelog(map);
			return new ResponseEntity("SUCCESS", HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL",HttpStatus.BAD_REQUEST);
		}
	}
	
	//메인페이지 오프라인조인 7개
	@GetMapping(value="/mainofflist")
	public ResponseEntity<List<MainJoinDto>> mainofflist(){
		try {
			System.out.println(">>> mainofflist");
			List<MainJoinDto> list = joinService.mainofflist();
			return new ResponseEntity<List<MainJoinDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	//메인페이지 온라인조인 7개
	@GetMapping(value="/mainonlist")
	public ResponseEntity<List<MainJoinDto>> mainonlist(){
		try {
			System.out.println(">>> mainonlist");
			List<MainJoinDto> list = joinService.mainonlist();
			return new ResponseEntity<List<MainJoinDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	// 사용자가 속해있는 조인 리스트 조회
	@GetMapping(value="/myjoinlist/{userid}")
	public ResponseEntity<List<CalendarDto>> myjoinlist(@PathVariable String userid){
		System.out.println(">>> myjoinlist");
		try {
			List<CalendarDto> list = joinService.myjoinlist(userid);
			return new ResponseEntity<List<CalendarDto>>(list, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
	}
	
	//조인 정보 상세보기
	@GetMapping(value="/detailjoin")
	public ResponseEntity<JSONObject> detailjoin(@RequestParam int roomNo, String userid){
		System.out.println(">>> detailjoin  roomNo : "+ roomNo);
		Map<String, String> map = new HashMap<String, String>();
		map.put("roomNo", Integer.toString(roomNo));
		map.put("userid", userid);
		try {
			JoinDetailDto joinDetailDto = joinService.joindetail(map);
			List<JoinMemberDto> members = joinService.joinmembers(roomNo);
			JSONObject jsonobject = new JSONObject();
			// roomNo, type, hostid, date, time, nowcount, totalcount, pw, title, body, thumbnail
			jsonobject.put("roomNo", joinDetailDto.getRoomNo());
			jsonobject.put("type", joinDetailDto.getType());
			jsonobject.put("date", joinDetailDto.getDate());
			jsonobject.put("time", joinDetailDto.getTime());
			jsonobject.put("nowcount", joinDetailDto.getNowcount());
			jsonobject.put("totalcount", joinDetailDto.getTotalcount());
			jsonobject.put("place", joinDetailDto.getPlace());
			jsonobject.put("lat", joinDetailDto.getLat());
			jsonobject.put("lon", joinDetailDto.getLon());
			jsonobject.put("pw", joinDetailDto.getPw());
			jsonobject.put("title", joinDetailDto.getTitle());
			jsonobject.put("body", joinDetailDto.getBody());
			jsonobject.put("thumbnail", joinDetailDto.getThumbnail());
			jsonobject.put("isapply", joinDetailDto.getIsapply());
			jsonobject.put("isaccept", joinDetailDto.getIsaccept());
			JSONArray jarray = new JSONArray();
			for(JoinMemberDto jmd : members) {
				JSONObject jobject = new JSONObject();
				jobject.put("nickname", jmd.getNickname());
				jobject.put("age", jmd.getAge());
				jobject.put("hit", jmd.getHit());
				jobject.put("profile", jmd.getProfile());
				jarray.add(jobject);
			}
			jsonobject.put("members", jarray);
			return new ResponseEntity<JSONObject>(jsonobject, HttpStatus.OK);
		
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@PostMapping("/cancelApply")
	public ResponseEntity<String> cancelapply(@RequestBody Map<String, String> map){
		System.out.println(">>> cancelapply : "+ map.get("roomNo") + " userid : "+ map.get("userid"));
		try {
//			Map<String, String> map = new HashMap<String, String>();
//			map.put("roomNo", roomNo);
//			map.put("userid", userid);
			if(joinService.cancelapply(map)) {
				return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity("FAIL", HttpStatus.BAD_REQUEST);
	}
//	@PostMapping(value="/offline")
//	public ResponseEntity registofflinejoin(@RequestBody Map<String,String> map) {
//		try {
//			if(joinService.registjoin(map)) {
//				return new ResponseEntity(HttpStatus.OK);
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//		return new ResponseEntity(HttpStatus.BAD_REQUEST);
//	}
}
