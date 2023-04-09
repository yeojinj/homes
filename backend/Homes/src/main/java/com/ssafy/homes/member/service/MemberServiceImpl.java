package com.ssafy.homes.member.service;

import java.util.HashMap;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.homes.member.model.MemberDto;
import com.ssafy.homes.member.model.mapper.MemberMapper;

@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private SqlSession sqlSession;

	/** 아이디 중복 체크 **/
	@Override
	public boolean idCheck(String userId) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).idCheck(userId) == 0;
	}

	/** 회원가입 **/
	@Override
	public boolean joinMember(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).joinMember(memberDto) == 1;
	}

	/** 로그인 하기 **/
	@Override
	public MemberDto loginMember(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).loginMember(memberDto);
	}

	/** 회원 탈퇴 **/
	@Override
	public boolean deleteMember(String userId) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).deleteMember(userId) == 1;
	}

	/** 회원 정보 수정 **/
	@Override
	public boolean updateMember(MemberDto memberDto) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).updateMember(memberDto) == 1;
	}

	/***** Token **************/

	@Override
	public MemberDto userInfo(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).userInfo(userid);
	}

	// RefreshToken을 저장해라
	@Override
	public void saveRefreshToken(String userid, String refreshToken) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", refreshToken);
		sqlSession.getMapper(MemberMapper.class).saveRefreshToken(map);
	}

	@Override
	public Object getRefreshToken(String userid) throws Exception {
		return sqlSession.getMapper(MemberMapper.class).getRefreshToken(userid);
	}

	@Override
	public void deleRefreshToken(String userid) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("userid", userid);
		map.put("token", null);
		sqlSession.getMapper(MemberMapper.class).deleteRefreshToken(map);
	}

}
