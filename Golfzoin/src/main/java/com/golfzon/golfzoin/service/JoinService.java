package com.golfzon.golfzoin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

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

public interface JoinService {
	// 온라인 조인 등록
	public boolean registonjoin(Map<String, String> map) throws SQLException;
	// 오프라인 조인 등록
	public boolean registoffjoin(Map<String, String> map) throws SQLException;
	// 조인 등록 시 joinlistuser테이블에 모집장 추가
	public boolean registhost(Map<String, String> map) throws SQLException;
	// 주변 오프라인 모집 조회
	// 온라인 조인 조회
	public List<JoinOnDto> onlist(Map<String, String> map) throws SQLException;
	public List<JoinOffDto> nearofflist(Map<String, String> map) throws SQLException;
	// 해당 오프라인 조인 인원 목록 조회
	public List<JoinMemberDto> joinmembers(int roomNo) throws SQLException;
	//오프라인 조인 신청
	public boolean applyjoin(Map<String, String> map) throws SQLException;
	// 호스트 알람 용 해당 조인 수락 대기 사용자 조회
	public List<HostAlarmDto> jointempuser(String userid) throws SQLException;
	
	// 조인 신청 수락 조인 신청 수락 후 조인 리스트 유저 테이블에 추가
	public boolean acceptuser(Map<String, String> map) throws SQLException;
	// 조인 거절/수락 후 대기상태 db에서 삭제
	public boolean removetempuser(Map<String, String> map) throws SQLException;
	// 조인 거절/수락 후 logDB 에 추가
	public boolean insertjoinlog(Map<String, String> map) throws SQLException;
	// 조인 신청 거절
	public boolean refuseuser(Map<String, String> map) throws SQLException;
	
	// 조인 등록 취소
	public boolean canceljoin(String roomNo) throws SQLException;
	public boolean cancelmember(String roomNo) throws SQLException;
	
	
	// 메인페이지 오프라인
	public List<MainJoinDto> mainofflist() throws SQLException;
	// 메인페이지 온라인 리스트
	public List<MainJoinDto> mainonlist() throws SQLException;
		
	
	
	// 알림 - 조인 신청했던 수락/거절 로그 조회
	public List<JoinLogDto> showlog(String userid) throws SQLException;
		
	// 알림- 알림- 사용자가 확인하고 삭제버튼 누르면 해당 로그 삭제
	public boolean deletelog(Map<String, String> map) throws SQLException;
	
	// 사용자가 속해있는 조인 리스트 조회
	public List<CalendarDto> myjoinlist(String userid) throws SQLException;
	

	// 특정 방 번호로 방 정보 상세 조회
	public JoinDetailDto joindetail(Map<String, String> map) throws SQLException;

	// 신청한 조인 취소
	public boolean cancelapply(Map<String, String> map) throws SQLException;
}
