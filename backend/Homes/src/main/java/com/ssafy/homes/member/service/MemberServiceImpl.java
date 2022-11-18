package com.ssafy.homes.user.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.homes.user.model.UserDto;
import com.ssafy.homes.user.model.mapper.UserMapper;


@Service
public class UserServiceImpl implements UserService{

	
	@Autowired
	private SqlSession sqlSession;
	
	/** 아이디 중복 체크 **/
	@Override
	public boolean idCheck(String userId) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(UserMapper.class).idCheck(userId)==1;
	}

	/** 회원가입 **/
	@Override
	public boolean joinMember(UserDto userDto) throws Exception {
		//TODO 값 제대로 안들어 왔을때 예외처리 하기 
	  return sqlSession.getMapper(UserMapper.class).joinMember(userDto) ==1;
		
	}
   /** 로그인 하기 **/
	@Override
	public UserDto loginMember(UserDto userDto) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(UserMapper.class).loginMember(userDto);
	}
	
	
	
	
	/***** Token **************/
	
	@Override
	public UserDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).userInfo(userid);
	}
	
	
	// RefreshToken을 저장해라 
	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		sqlSession.getMapper(UserMapper.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return sqlSession.getMapper(UserMapper.class).getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		sqlSession.getMapper(UserMapper.class).deleteRefreshToken(map);
	}

}
