package com.ssafy.homes.qna.service;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.homes.qna.model.QnaDto;


public interface QnaService {
	/** qna 작성 **/
	public boolean writeQna(QnaDto qnaDto) throws Exception;
	/** qna 목록 불러오기 **/
	public List<QnaDto> listQna( ) throws Exception;
	/** qna 글 정보 불러오기 **/
	public QnaDto getQna(int no) throws Exception;
	/** qna 글 수정하기 **/
	public boolean modifyQna(QnaDto qnaDto) throws Exception;
	/** qna 글 삭제하기 **/
	public boolean deleteQna(int no) throws Exception;
	/** 유저아이디가지고 qna글 삭제하기 **/
	public void deleteQnaWithUserId(String userId) throws Exception;
	/** 답글 달기 (관리자)**/
	public boolean writeComment(QnaDto qnaDto) throws Exception;
	
	
	

}
