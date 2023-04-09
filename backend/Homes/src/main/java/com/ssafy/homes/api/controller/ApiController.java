package com.ssafy.homes.api.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin("*")
@RequestMapping(value="/api", produces="application/json; charset=utf8")
public class ApiController {
	
	@GetMapping("/news")
	public ResponseEntity<String> newsApi() {
		
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("X-Naver-Client-Id", "wuGoD8_KqUGP3dudu7Ft");
		headers.add("X-Naver-Client-Secret", "EWakrhwpny");
		
		HttpEntity<MultiValueMap<String, String>> newsRequest = new HttpEntity<>(headers);
		
		String keyword = "부동산";
		
		ResponseEntity<String> response = rt.exchange("https://openapi.naver.com/v1/search/news.json?query=" + keyword + "&display=2&sort=sim", HttpMethod.GET,
				newsRequest, String.class);
				
		return new ResponseEntity<>(response.getBody(), HttpStatus.OK);
	}
}
