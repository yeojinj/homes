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
	//아파트 목록 불러오기 
	List<HouseInfoDto> getAptInfoInDong (Map<String,String> map) throws Exception;
}
