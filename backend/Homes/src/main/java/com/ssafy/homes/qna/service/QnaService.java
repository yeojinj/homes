package com.ssafy.homes.qna.service;

import java.util.List;

import com.ssafy.homes.qna.model.QnaDto;


public interface QnaService {
	
	public boolean writeQna(QnaDto qnaDto) throws Exception;
	public List<QnaDto> listQna( ) throws Exception;
	public QnaDto getQna(int no) throws Exception;
	public boolean modifyQna(QnaDto qnaDto) throws Exception;
	public boolean deleteQna(int no) throws Exception;
	public void deleteQnaWithUserId(String userId) throws Exception;

}
