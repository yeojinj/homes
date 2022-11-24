package com.ssafy.homes.member.service;

import java.util.List;
import java.util.Map;

import com.ssafy.homes.member.model.MemberDto;


public interface MemberService {
	
	// 아이디 중복 체크
	boolean idCheck(String userId) throws Exception;
	// 회원 가입
	boolean joinMember(MemberDto memberDto) throws Exception;
	// 로그인
	MemberDto loginMember(MemberDto memberDto) throws Exception;
	//회원 탈퇴
	boolean deleteMember(String userId) throws Exception;
	//회원 정보 수정 
	boolean updateMember(MemberDto memberDto) throws Exception;
	
	/**Token**/
	public MemberDto userInfo(String userid) throws Exception;
	public void saveRefreshToken(String userid, String refreshToken) throws Exception;
	public Object getRefreshToken(String userid) throws Exception;
	public void deleRefreshToken(String userid) throws Exception;

}
