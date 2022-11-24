package com.ssafy.homes.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.homes.notice.model.NoticeDto;

@Mapper
public interface NoticeMapper {

	/** 글쓰기 **/
	int writeArticle(NoticeDto noticeDto) throws SQLException;
	/** 글 목록 조회 **/
	List<NoticeDto> listArticle(Map<String, Object> map) throws SQLException;
	/** 글 총 개수 조회ㅗ **/
	int getTotalArticleCount(Map<String, Object> map) throws SQLException;
	/** 글 조회 **/
	NoticeDto getArticle(int articleNo) throws SQLException;
	/** 조회수 수정 **/
	void updateHit(int articleNo) throws SQLException;
	/** 글 수정 **/
	int modifyArticle(NoticeDto noticeDto) throws SQLException;
	/** 글 삭제 **/
	int deleteArticle(int articleNo) throws SQLException;
	
}
