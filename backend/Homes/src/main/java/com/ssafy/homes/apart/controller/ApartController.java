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

	// GET :시도 정보
	@GetMapping("/sido")
	public ResponseEntity<Map<String, Object>> sido() throws Exception {

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

	@GetMapping("/gugun")
	public ResponseEntity<List<SiGunDongDto>> gugun(@RequestParam("sido") String sido) throws Exception {
		logger.info("gugun - 호출");
		return new ResponseEntity<List<SiGunDongDto>>(apartService.getGugunInSido(sido), HttpStatus.OK);
	}

	@GetMapping("/dong")
	public ResponseEntity<List<SiGunDongDto>> dong(@RequestParam("gugun") String gugun) throws Exception {
		logger.info("dong - 호출");

		return new ResponseEntity<List<SiGunDongDto>>(apartService.getDongInGugun(gugun), HttpStatus.OK);
	}

	@GetMapping("/apartList")
	public ResponseEntity<List<HouseInfoDto>> aptInfo(@RequestParam("dongCode") String dong,
			@RequestParam("apartName") String apartName) throws Exception {
		// ,@RequestParam("apartName") String apartName

		System.out.println(dong);
		Map<String, String> map = new HashMap<String, String>();
		map.put("dongCode", dong);
		map.put("apartName", apartName);

		System.out.println(apartService.getAptInfoInDong(map));

		return new ResponseEntity<List<HouseInfoDto>>(apartService.getAptInfoInDong(map), HttpStatus.OK);
	}

	@GetMapping("/view/{apartCode}/{area}")
	public ResponseEntity<List<Map<String, Object>>> getYearMonthAmount(

			@PathVariable("apartCode") String aptCode ,@PathVariable("area") String area) throws Exception {

		Map<String, String> map = new HashMap();
		map.put("aptCode", aptCode);
		map.put("area",area);

		logger.info("연월 기준 평균 거래 값  - 호출");
		return new ResponseEntity<List<Map<String, Object>>>(apartService.getYearMonthAmount(map), HttpStatus.OK);
	}

	@GetMapping("/area/{apartCode}")
	public ResponseEntity<List<String>> getArea(
			@PathVariable("apartCode") String aptCode) throws Exception {

		logger.info("해당 아파트 모든 평수 얻기  - 호출");
		return new ResponseEntity<List<String>>(apartService.getArea(aptCode), HttpStatus.OK);
	}
}
