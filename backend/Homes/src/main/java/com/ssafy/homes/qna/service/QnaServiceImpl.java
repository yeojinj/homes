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

	
	// Insert : Qna 등록
	@Override
	public boolean writeQna(QnaDto qnaDto) throws Exception {
		if(qnaDto.getSubject() == null || qnaDto.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(QnaMapper.class).writeQna(qnaDto) == 1;
	}
	//GET: Qna 전제 글 읽어오기
	@Override
	public List<QnaDto> listQna() throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QnaMapper.class).listQna();
	}
   //Get One : 상세 글 가져오기
	@Override
	public QnaDto getQna(int no) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QnaMapper.class).getQna(no);
	}
	// UPDATE 사용자 : Qna  수정
	@Override
	public boolean modifyQna(QnaDto qnaDto) throws Exception {
		// TODO Auto-generated method stub
		return sqlSession.getMapper(QnaMapper.class).modifyQna(qnaDto) == 1;
	}
	// DELETE  : Qna 삭제  - 글 번호

	@Override
	public boolean deleteQna(int no) throws Exception {
		return sqlSession.getMapper(QnaMapper.class).deleteQna(no) == 1;
	}
	
	// DELETE  Qna 삭제  -  유저 아이디
	@Override
	public void deleteQnaWithUserId(String userId) throws Exception {
		// TODO Auto-generated method stub
	 sqlSession.getMapper(QnaMapper.class).deleteQnaWithUserId(userId);
	}


}
