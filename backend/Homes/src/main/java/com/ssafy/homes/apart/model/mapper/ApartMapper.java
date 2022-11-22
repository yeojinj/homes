package com.ssafy.homes.apart.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.homes.apart.model.HouseInfoDto;
import com.ssafy.homes.apart.model.SiGunDongDto;
@Mapper
public interface ApartMapper {
	
	//시도 정보 읽어오기
	List<SiGunDongDto> getSido() throws SQLException;
	
	//구군 정보 읽어오기 
	List<SiGunDongDto> getGugunInSido(String sido) throws SQLException;
	
	//동 정보 얻어오기
	List<SiGunDongDto> getDongInGugun(String gugun) throws SQLException;
	
	
	//아파트 목록 불러오기 
	List<HouseInfoDto> getAptInfoInDong (Map<String,String> map) throws SQLException;
	
	
	//연월기준 평균 거래 금액 얻어오기 
	List<Map<String, Object>> getYearMonthAmount(Map<String, String> map) throws SQLException;
	
	//해당 아파트의 모든 평수를 얻어온다
	List <String> getArea(String aptCode) throws SQLException;


 }
