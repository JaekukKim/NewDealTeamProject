package com.studycafe.utils.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import com.studycafe.utils.file.service.S3ServiceImpl;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CKEditorController {
	
	@Autowired
	private S3ServiceImpl s3Service;
	
	@Value("${cloud.aws.s3.objurl}")
	private String objectUrl;
	
	// CK Editor 경로변수
	private final String path = "ck";
	
	@ResponseBody
	@PostMapping(value = "/ck/teamregisimgupload")
	public ModelAndView ckImgUpload(MultipartHttpServletRequest file , Model model) throws Exception {
		
		log.info("ck에디터 파일 업로드 시작");
		
		ModelAndView mav = new ModelAndView("jsonView");

		MultipartFile uploadFile = file.getFile("upload");
		
		log.info("파일은? : {}", uploadFile);
		
		mav.addObject("uploaded", true); // 업로드 완료
		mav.addObject("url", objectUrl + s3Service.saveFile(uploadFile, path)); // 업로드 파일의 경로
		
		log.info("mav 최종 객체는? : {}", mav);
		
		return mav;

	}
}
