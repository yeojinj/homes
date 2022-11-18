package com.ssafy.homes.user.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import com.ssafy.homes.user.model.UserDto;


public interface UserMapper {
	int idCheck(String userId) throws SQLException;
	int joinMember(UserDto userDto) throws SQLException;
	UserDto loginMember(UserDto userDto) throws SQLException;
	
	
	
	
	
	
	/****** Token**************/
	public UserDto userInfo(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
}
