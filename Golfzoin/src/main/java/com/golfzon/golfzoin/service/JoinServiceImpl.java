package com.golfzon.golfzoin.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
import com.golfzon.golfzoin.mapper.JoinMapper;
@Service
public class JoinServiceImpl implements JoinService {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public boolean registonjoin(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).registonjoin(map) == 1;
	}

	@Override
	public boolean registoffjoin(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).registoffjoin(map) == 1;
	}

	@Override
	public boolean registhost(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).registhost(map) == 1;
	}
	
	@Override
	public List<JoinOffDto> nearofflist(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).nearofflist(map);
	}
	@Override
	public List<JoinMemberDto> joinmembers(int roomNo) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).joinmembers(roomNo);
	}
	@Override
	public boolean applyjoin(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).applyjoin(map) == 1;
	}

	
	
	
	@Override
	public boolean canceljoin(String roomNo) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).canceljoin(roomNo) == 1;
	}
	
	@Override
	public boolean cancelmember(String roomNo) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).cancelmember(roomNo) == 1;
	}
	@Override
	public List<HostAlarmDto> jointempuser(String userid) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).jointempuser(userid);
	}

	@Override
	public boolean acceptuser(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).acceptuser(map) == 1;
	}

	@Override
	public boolean refuseuser(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).refuseuser(map) == 1;
	}

	@Override
	public boolean removetempuser(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).removetempuser(map) == 1;
	}

	@Override
	public boolean insertjoinlog(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).insertjoinlog(map) == 1;
	}

	@Override
	public List<JoinLogDto> showlog(String userid) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).showlog(userid);
	}

	@Override
	public boolean deletelog(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).deletelog(map) == 1;
	}

	@Override
	public List<MainJoinDto> mainofflist() throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).mainofflist();
	}

	@Override
	public List<MainJoinDto> mainonlist() throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).mainonlist();
	}

	@Override
	public List<JoinOnDto> onlist(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).onlist(map);
	}

	@Override
	public List<CalendarDto> myjoinlist(String userid) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).myjoinlist(userid);
	}

	@Override
	public JoinDetailDto joindetail(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).joindetail(map);
	}

	@Override
	public boolean cancelapply(Map<String, String> map) throws SQLException {
		return sqlSession.getMapper(JoinMapper.class).cancelapply(map) == 1;
	}

	


	

}
