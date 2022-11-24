package com.ssafy.homes.apart.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;

import com.ssafy.homes.apart.model.HouseInfoDto;
import com.ssafy.homes.apart.model.SiGunDongDto;
import com.ssafy.homes.apart.service.ApartService;

import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/apart")
public class ApartController {

	private final Logger logger = LoggerFactory.getLogger(ApartController.class);

	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private ApartService apartService;

	// GET :시 정보 얻기
	@GetMapping("/sido")
	public ResponseEntity<Map<String, Object>> sido() throws Exception {
		
		//logger.info("sido- 호출");

		List<SiGunDongDto> resultList = apartService.getSido();
		Map<String, Object> resultMap = new HashMap<>();

		if (resultList != null) {
			resultMap.put("message", SUCCESS);
			resultMap.put("sido", resultList);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.OK);
		} else {
			resultMap.put("message", FAIL);
			return new ResponseEntity<Map<String, Object>>(resultMap, HttpStatus.NO_CONTENT);
		}

	}
	//GET :구군 정보 얻기
	@GetMapping("/gugun")
	public ResponseEntity<List<SiGunDongDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		
		//logger.info("gugun - 호출");
		
		return new ResponseEntity<List<SiGunDongDto>>(apartService.getGugunInSido(sido), HttpStatus.OK);
	}
	//GET : 동 정보 얻기
	@GetMapping("/dong")
	public ResponseEntity<List<SiGunDongDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		logger.info("dong - 호출");

		return new ResponseEntity<List<SiGunDongDto>>(apartService.getDongInGugun(gugun), HttpStatus.OK);
	}
	//GET : 해당하는 동 코드의 전체 아파트 목록 얻기
	@GetMapping("/apartList")
	public ResponseEntity<List<HouseInfoDto>> aptInfo(@RequestParam("dongCode") String dong,
			@RequestParam("apartName") String apartName) throws Exception {
		

		//logger.info("apartList with dongcode - 호출");
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("dongCode", dong);
		map.put("apartName", apartName);

		System.out.println(apartService.getAptInfoInDong(map));

		return new ResponseEntity<List<HouseInfoDto>>(apartService.getAptInfoInDong(map), HttpStatus.OK);
	}
	//GET : 아파트코드와 평수에 따른 거래년,월과 금액 얻어오기
	@GetMapping("/view/{apartCode}/{area}")
	public ResponseEntity<List<Map<String, Object>>> getYearMonthAmount(

			@PathVariable("apartCode") String aptCode ,@PathVariable("area") String area) throws Exception {
		logger.info(" getYearMonthAmount- 호출");
		Map<String, String> map = new HashMap();
		map.put("aptCode", aptCode);
		map.put("area",area);

		
		return new ResponseEntity<List<Map<String, Object>>>(apartService.getYearMonthAmount(map), HttpStatus.OK);
	}

	//GET : 해당아파트에 존재하는 모든 평수 얻어오기
	@GetMapping("/area")
	public ResponseEntity<List<String>> getArea(
			@RequestParam("apt") String aptCode) throws Exception {

		logger.info("해당 아파트 모든 평수 얻기  - 호출");
		return new ResponseEntity<List<String>>(apartService.getArea(aptCode), HttpStatus.OK);
	}
	
	//GET : 아파트코드와 평수에 따른 거래 상세 정보 얻어오기
	@GetMapping("/dealList/{apartCode}/{area}")
	public ResponseEntity<List<Map<String, Object>>> getDealList(

			@PathVariable("apartCode") String aptCode ,@PathVariable("area") String area) throws Exception {

		Map<String, String> map = new HashMap();
		map.put("aptCode", aptCode);
		map.put("area",area);

		logger.info("거래 리스트  - 호출");
		return new ResponseEntity<List<Map<String, Object>>>(apartService.getDealList(map), HttpStatus.OK);
	}
	
	
	//GET : 아파트 정보 얻어오기
	
	@GetMapping("/apartInfo")
	public ResponseEntity<HouseInfoDto> getApartInfo(

			@RequestParam("apt") String aptCode) throws Exception {
		logger.info("아파트 정보 info  - 호출");
		return new ResponseEntity<HouseInfoDto>(apartService.getApartInfo(aptCode), HttpStatus.OK);
	}
	
	
}
