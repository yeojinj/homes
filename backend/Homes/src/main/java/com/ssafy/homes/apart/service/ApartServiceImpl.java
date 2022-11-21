package com.ssafy.homes.apart.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.homes.apart.model.HouseInfoDto;
import com.ssafy.homes.apart.model.SiGunDongDto;
import com.ssafy.homes.apart.model.mapper.ApartMapper;
@Service
public class ApartServiceImpl implements ApartService{
	
	@Autowired
	private SqlSession sqlSession;
	//시도 정보 얻어오기
	@Override
	public List<SiGunDongDto> getSido() throws Exception {
		
		return sqlSession.getMapper(ApartMapper.class).getSido();
	}
    //구군 정보 얻어오기
	@Override
	public List<SiGunDongDto> getGugunInSido(String sido) throws Exception {
		return sqlSession.getMapper(ApartMapper.class).getGugunInSido(sido);
	}

	@Override
	public List<SiGunDongDto> getDongInGugun(String gugun) throws Exception {
		return sqlSession.getMapper(ApartMapper.class).getDongInGugun(gugun);
	}
	@Override
	public List<HouseInfoDto> getAptInfoInDong(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(ApartMapper.class).getAptInfoInDong(map);
	}

}