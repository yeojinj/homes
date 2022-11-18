package com.ssafy.homes.user.service;

import java.util.List;
import java.util.Map;

import com.ssafy.homes.user.model.UserDto;


public interface UserService {
	
	// 아이디 중복 체크
	boolean idCheck(String userId) throws Exception;
	//회원 가입
	boolean joinMember(UserDto memberDto) throws Exception;
	//로그인
	UserDto loginMember(UserDto userDto) throws Exception;
	
	
	
	
	
	/**Token**/
	public UserDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;

}
