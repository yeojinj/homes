package com.ssafy.homes.member.model.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.homes.member.model.MemberDto;

@Mapper
public interface MemberMapper {
	
	/** 아이디 중복 체크 **/
	int idCheck(String userId) throws SQLException;
	/** 회원 가입 **/
	int joinMember(MemberDto memberDto) throws SQLException;
	/** 로그인  **/
	MemberDto loginMember(MemberDto memberDto) throws SQLException;
	/** 회원 탈퇴 **/
	int deleteMember (String userId) throws SQLException;
	/** 회원 정보 수정 **/
	int updateMember (MemberDto memberDto) throws SQLException;
	
	/****** Token**************/
	public MemberDto userInfo(String userid) throws SQLException;
	public void saveRefreshToken(Map<String, String> map) throws SQLException;
	public Object getRefreshToken(String userid) throws SQLException;
	public void deleteRefreshToken(Map<String, String> map) throws SQLException;
}
