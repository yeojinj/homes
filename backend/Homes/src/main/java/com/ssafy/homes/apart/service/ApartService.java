package com.ssafy.homes.apart.service;

import java.util.List;
import java.util.Map;

import com.ssafy.homes.apart.model.HouseInfoDto;
import com.ssafy.homes.apart.model.SiGunDongDto;

public interface ApartService {
	
	//시도 정보 얻어오기
	List<SiGunDongDto> getSido() throws Exception;
	//구군 정보 얻어오기
	List<SiGunDongDto> getGugunInSido(String sido) throws Exception;
	//동 정보 얻어오기 
	List<SiGunDongDto> getDongInGugun(String gugun) throws Exception;
	//아파트 목록 얻어오기 
	List<HouseInfoDto> getAptInfoInDong (Map<String,String> map) throws Exception;
	
	//연월 기준 평균 거래금액 얻어오기 
	List<Map<String,Object>> getYearMonthAmount (Map<String , String> map) throws Exception;
	
	//해당하는 아파트의 모든 평수를 얻어온다.
	List<String> getArea (String aptCode) throws Exception;
	
	
	//해당 아파트의 평수 기준 모든 거래내역을 불러온다.
	List<Map<String,Object>> getDealList (Map<String,String> map) throws Exception;
}
