package com.ssafy.homes.qna.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.homes.qna.model.QnaDto;
import com.ssafy.homes.qna.model.mapper.QnaMapper;

@Service
public class QnaServiceImpl  implements QnaService{
	@Autowired
	private SqlSession sqlSession;
	
	// Insert : QnA 등록
	@Override
	public boolean writeQna(QnaDto qnaDto) throws Exception {
		if (qnaDto.getSubject() == null || qnaDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(QnaMapper.class).writeQna(qnaDto) == 1;
	}
	
	// GET : QnA 목록
	@Override
	public List<QnaDto> listQna() throws Exception {
		return sqlSession.getMapper(QnaMapper.class).listQna();
	}
	
    // GET : 상세 글 - 글 번호
	@Override
	public QnaDto getQna(int no) throws Exception {
		return sqlSession.getMapper(QnaMapper.class).getQna(no);
	}
	
	// PUT : QnA 수정
	@Override
	public boolean modifyQna(QnaDto qnaDto) throws Exception {
		return sqlSession.getMapper(QnaMapper.class).modifyQna(qnaDto) == 1;
	}
	
	// DELETE : QnA 삭제  - 글 번호
	@Override
	public boolean deleteQna(int no) throws Exception {
		return sqlSession.getMapper(QnaMapper.class).deleteQna(no) == 1;
	}
	
	// DELETE : QnA 삭제  - 회원 아이디
	@Override
	public void deleteQnaWithUserId(String userId) throws Exception {
		sqlSession.getMapper(QnaMapper.class).deleteQnaWithUserId(userId);
	}

	// PUT : QnA 답변 등록
	@Override
	public boolean writeComment(QnaDto qnaDto) throws Exception {
		return sqlSession.getMapper(QnaMapper.class).writeComment(qnaDto) == 1;
	}

}
