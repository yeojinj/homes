package com.ssafy.homes.notice.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.homes.notice.model.NoticeDto;
import com.ssafy.homes.notice.model.mapper.NoticeMapper;
import com.ssafy.homes.util.PageNavigation;
import com.ssafy.homes.util.SizeConstant;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	private NoticeMapper noticeMapper;
	
	@Autowired
	public NoticeServiceImpl(NoticeMapper noticeMapper) {
		this.noticeMapper = noticeMapper;
	}

	@Override
	@Transactional
	public boolean writeArticle(NoticeDto noticeDto) throws Exception {
		if(noticeDto.getSubject() == null || noticeDto.getContent() == null) {
			throw new Exception();
		}
		return noticeMapper.writeArticle(noticeDto) == 1;
	}

	@Override
	public List<NoticeDto> listArticle(Map<String, String> map) throws Exception {
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "b.user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int pgNo = Integer.parseInt(map.get("pgno") == null ? "1" : map.get("pgno"));
		int start = pgNo * SizeConstant.LIST_SIZE - SizeConstant.LIST_SIZE;
		param.put("start", start);
		param.put("listsize", SizeConstant.LIST_SIZE);

		return noticeMapper.listArticle(param);
	}

	@Override
	public PageNavigation makePageNavigation(Map<String, String> map) throws Exception {
		PageNavigation pageNavigation = new PageNavigation();

		int naviSize = SizeConstant.NAVIGATION_SIZE;
		int sizePerPage = SizeConstant.LIST_SIZE;
		int currentPage = Integer.parseInt(map.get("pgno"));

		pageNavigation.setCurrentPage(currentPage);
		pageNavigation.setNaviSize(naviSize);
		Map<String, Object> param = new HashMap<String, Object>();
		String key = map.get("key");
		if ("userid".equals(key))
			key = "user_id";
		param.put("key", key == null ? "" : key);
		param.put("word", map.get("word") == null ? "" : map.get("word"));
		int totalCount = noticeMapper.getTotalArticleCount(param);
		pageNavigation.setTotalCount(totalCount);
		int totalPageCount = (totalCount - 1) / sizePerPage + 1;
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = currentPage <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < currentPage;
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();

		return pageNavigation;
	}

	@Override
	public NoticeDto getArticle(int no) throws Exception {
		return noticeMapper.getArticle(no);
	}

	@Override
	public void updateHit(int no) throws Exception {
		noticeMapper.updateHit(no);
	}

	@Override
	public boolean modifyArticle(NoticeDto noticeDto) throws Exception {
		return noticeMapper.modifyArticle(noticeDto) == 1;
	}

	@Override
	@Transactional
	public boolean deleteArticle(int no) throws Exception {
		return noticeMapper.deleteArticle(no) == 1;
	}

}
