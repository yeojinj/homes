package com.ssafy.homes.qna.model.mapper;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.homes.qna.model.QnaDto;

@Mapper
public interface QnaMapper {
	/** qna 작성 **/
	public int writeQna(QnaDto qnaDto) throws SQLException;
	/** qna 목록 불러오기 **/
	public List<QnaDto> listQna( ) throws SQLException;
	/** qna 글 정보 불러오기 **/
	public QnaDto getQna(int no) throws SQLException;
	/** qna 글 수정하기 **/
	public int modifyQna(QnaDto qnaDto) throws SQLException;
	/** qna 글 삭제하기 **/
	public int deleteQna(int no) throws SQLException;
	/** 유저아이디가지고 qna글 삭제하기 **/
	public void deleteQnaWithUserId (String userId) throws SQLException;
	/** 답글 달기 (관리자)**/
	public int writeComment(QnaDto qnaDto) throws SQLException;

}
