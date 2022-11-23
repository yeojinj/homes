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
	// 해당하는 아파트의 정보 얻어오기 
	@Override
	public List<SiGunDongDto> getDongInGugun(String gugun) throws Exception {
		return sqlSession.getMapper(ApartMapper.class).getDongInGugun(gugun);
	}
	@Override
	public List<HouseInfoDto> getAptInfoInDong(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(ApartMapper.class).getAptInfoInDong(map);
	}
	@Override
	public List<Map<String, Object>> getYearMonthAmount(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(ApartMapper.class).getYearMonthAmount(map);
	}
	@Override
	public List<String> getArea(String aptCode) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(ApartMapper.class).getArea(aptCode);
	}
	
	//해당 아파트의 평수 기준 모든 거래내역을 불러온다.
	@Override
	public List<Map<String, Object>> getDealList(Map<String, String> map) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(ApartMapper.class).getDealList(map);
	}
	
	// 해당하는 아파트의 정보 얻어오기 
	@Override
	public HouseInfoDto getApartInfo(String aptCode) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(ApartMapper.class).getApartInfo(aptCode);
	}

}
