package com.ssafy.homes.qna.controller;

import java.util.List;

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
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.homes.qna.model.QnaDto;
import com.ssafy.homes.qna.service.QnaService;

@RestController
@RequestMapping("/qna")
public class QnaController {
	
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";
	@Autowired
	private QnaService qnaService;
	
	// POST : Qna 등록 
	@PostMapping
	public ResponseEntity<String> writeQna(@RequestBody  QnaDto QnaDto) throws Exception {
		if (qnaService.writeQna(QnaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// GET : Qna 목록 긁어오기 
	@GetMapping
	public ResponseEntity<List<QnaDto>> listQna() throws Exception {
		return new ResponseEntity<List<QnaDto>>(qnaService.listQna(), HttpStatus.OK);
	}
	
	// GET ONE : Qna 상세 보기 
	@GetMapping("/{articleno}")
	public ResponseEntity<QnaDto> getQna(@PathVariable("articleno")  int no) throws Exception {
		return new ResponseEntity<QnaDto>(qnaService.getQna(no), HttpStatus.OK);
	}
	
	// UPDATE : QNA 수정 
	@PutMapping
	public ResponseEntity<String> modifyQna(@RequestBody QnaDto QnaDto) throws Exception {
		if (qnaService.modifyQna(QnaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	// DELETE : QNA 삭제
	@DeleteMapping("/{no}")
	public ResponseEntity<String> deleteQna(@PathVariable("no") int no) throws Exception {
		if (qnaService.deleteQna(no)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	// PUT : QnA 답변 등록
	@PutMapping("/comment")
	public ResponseEntity<String> writeComment(@RequestBody QnaDto QnaDto) throws Exception {
		if (qnaService.writeComment(QnaDto)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
}
