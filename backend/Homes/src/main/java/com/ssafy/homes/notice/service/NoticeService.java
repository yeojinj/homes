package com.ssafy.homes.notice.service;

import java.util.List;
import java.util.Map;

import com.ssafy.homes.notice.model.NoticeDto;
import com.ssafy.homes.util.PageNavigation;

public interface NoticeService {
	/** 글쓰기 **/
	boolean writeArticle(NoticeDto noticeDto) throws Exception;
	/** 글 목록 조회 **/
	List<NoticeDto> listArticle(Map<String, String> map) throws Exception;
	/** 페이징 **/
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	/** 글 조회 **/
	NoticeDto getArticle(int no) throws Exception;
	/** 조회수 수정 **/
	void updateHit(int no) throws Exception;
	/** 글 수정 **/
	boolean modifyArticle(NoticeDto noticeDto) throws Exception;
	/** 글 삭제 **/
	boolean deleteArticle(int no) throws Exception;
	
	
	
	
	
	
	
	
}
