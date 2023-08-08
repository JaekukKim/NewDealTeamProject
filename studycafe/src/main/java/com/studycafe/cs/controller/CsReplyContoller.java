package com.studycafe.cs.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studycafe.cs.entity.CsEntity;
import com.studycafe.cs.entity.CsReplyEntity;
import com.studycafe.cs.service.CsReplyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CsReplyContoller {
	@Autowired
	private CsReplyService csReplyService;

	// 고객센터 모집 댓글 Ajax
	@PostMapping("/csReplyInsert")
	@ResponseBody
	public Map<String, Object> studyReplyInsert(@RequestBody CsReplyEntity csReplyEntity) {

		Map<String, Object> result = new HashMap<String, Object>();
		log.info("csReplyEntity : {}" , csReplyEntity.getCsEntity().getIdx());
		try {
			CsEntity csEntity = new CsEntity();
			csEntity = csReplyEntity.getCsEntity(); // StudyEntity 객체 생성

			int depth = csReplyEntity.getCsReplyDepth(); // 댓글 깊이 값 저장

			if (depth == 0) { // ref 값이 0이면 일반 댓글
				// 부모 게시글 번호 생성
				int count = csReplyService.CsReplyCount(csEntity.getIdx());
				csReplyEntity.setCsReplyRef(count); // 부모 게시글 번호 저장
			}

			csReplyService.CsReplyInsert(csReplyEntity); // 댓글 작성

			result.put("status", "ok");

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "fail");

			return result;
		}
	}

	// 고객센터 댓글 삭제
	@PostMapping("/csReplyDelete")
	@ResponseBody
	public Map<String, Object> csReplyDelete(@RequestBody CsReplyEntity csReplyEntity) {

		Map<String, Object> result = new HashMap<String, Object>();

		try {

			Long id = csReplyEntity.getCsReplyNum();

			csReplyService.CsReplyDelete(id);

			result.put("status", "ok");

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "fail");

			return result;
		}
	}

	// 고객센터 모집 댓글 Ajax
	@PostMapping("/csReplyModify")
	@ResponseBody
	public Map<String, Object> csReplyModify(@RequestBody CsReplyEntity csReplyEntity) {

		Map<String, Object> result = new HashMap<String, Object>();

		System.out.println("csReplyEntity : " + csReplyEntity);

		try {
			csReplyService.CsReplyInsert(csReplyEntity); // 댓글 작성

			result.put("status", "ok");

			return result;
		} catch (Exception e) {
			e.printStackTrace();
			result.put("status", "fail");

			return result;
		}
	}

	// 고객센터 댓글 리스트
	@PostMapping("/csReplyList")
	@ResponseBody
	public List<CsReplyEntity> csReplyList(@RequestBody CsReplyEntity csReplyEntity) {

		List<CsReplyEntity> list = null;
		CsEntity csEntity = null;

		csEntity = csReplyEntity.getCsEntity();

		int studyNum = (int) csEntity.getIdx();

		list = csReplyService.CsReplyList(studyNum);

		return list;
	}
}
