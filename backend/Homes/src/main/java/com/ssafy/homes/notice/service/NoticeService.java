package com.ssafy.homes.notice.service;

import java.util.List;
import java.util.Map;

import com.ssafy.homes.notice.model.NoticeDto;
import com.ssafy.homes.util.PageNavigation;

public interface NoticeService {

	boolean writeArticle(NoticeDto noticeDto) throws Exception;
	List<NoticeDto> listArticle(Map<String, String> map) throws Exception;
	PageNavigation makePageNavigation(Map<String, String> map) throws Exception;
	NoticeDto getArticle(int no) throws Exception;
	void updateHit(int no) throws Exception;
	boolean modifyArticle(NoticeDto noticeDto) throws Exception;
	boolean deleteArticle(int no) throws Exception;
	
}
