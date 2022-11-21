package com.ssafy.homes.notice.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homes.notice.model.NoticeDto;
import com.ssafy.homes.notice.service.NoticeService;

@RestController
@RequestMapping("/notice")
public class NoticeController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	
	@Autowired
	private NoticeService noticeService;

	// 글 쓰기 POST
	@PostMapping
	public ResponseEntity<String> writeNotice(@RequestBody NoticeDto noticeDto) throws Exception {
		if (noticeService.writeArticle(noticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// 글 전체 목록 GET/list
	// "/notice?pgno=1&key=&word="
	@GetMapping
	public ResponseEntity<List<NoticeDto>> listNotice(@RequestParam Map<String, String> map) throws Exception {
		return new ResponseEntity<List<NoticeDto>>(noticeService.listArticle(map), HttpStatus.OK);
	}
	
	// 글 상세 GET/no
	@GetMapping("/{no}")
	public ResponseEntity<NoticeDto> getNotice(@PathVariable("no") int no) throws Exception {
		noticeService.updateHit(no);
		return new ResponseEntity<NoticeDto>(noticeService.getArticle(no), HttpStatus.OK);
	}
	
	// 글 수정 PUT
	@PutMapping
	public ResponseEntity<String> modifyNotice(@RequestBody NoticeDto noticeDto) throws Exception {
		if (noticeService.modifyArticle(noticeDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// 글 삭제 DELETE/no
	@DeleteMapping("/{no}")
	public ResponseEntity<String> deleteNotice(@PathVariable("no") int no) throws Exception {
		if(noticeService.deleteArticle(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
}
