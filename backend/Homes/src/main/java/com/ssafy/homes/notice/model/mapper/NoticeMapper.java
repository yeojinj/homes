package com.ssafy.homes.notice.model.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.homes.notice.model.NoticeDto;

@Mapper
public interface NoticeMapper {

	int writeArticle(NoticeDto noticeDto) throws SQLException;
	List<NoticeDto> listArticle(Map<String, Object> map) throws SQLException;
	int getTotalArticleCount(Map<String, Object> map) throws SQLException;
	NoticeDto getArticle(int articleNo) throws SQLException;
	void updateHit(int articleNo) throws SQLException;
	int modifyArticle(NoticeDto noticeDto) throws SQLException;
	int deleteArticle(int articleNo) throws SQLException;
	
}
