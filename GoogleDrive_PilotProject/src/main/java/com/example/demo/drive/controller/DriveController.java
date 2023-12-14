package com.example.demo.drive.controller;

import java.io.Console;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.drive.service.IDriveService;

import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/drive")
@Slf4j
public class DriveController {
	@Autowired
	IDriveService driveService;
	
	private static final Logger logger = LoggerFactory.getLogger(DriveController.class);
	
	//테스트용도 getDirPath(String dirId) 해보기
	@GetMapping(value="/test")
	public String getDirPath(@RequestParam String dirId) {
		logger.info("test activate");
		return driveService.getDirPath(dirId);
	}
	
	//테스트용도 getHome
	@GetMapping(value="/home")
	public String getHome() {
		logger.info("/drive/home GET activate");
		return "drive/home";
	}
	
	//로그인 성공하면, 메인 화면으로
//	@GetMapping("/home")
//	public String getHome(Model model) {
//		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//		String memberId = auth.getName();
//		if (memberId != null && !memberId.equals("")) {
//			model.addAttribute("directoryList", driveService.getHomeDirectory(Integer.valueOf(memberId))); //List 가져온 것을 'directoryList' 로 model 에 담아서 반환
//			return "drive/home";
//		} else {
//			return "drive/home";
//		}
//	}
	

	//GET - /drive/home/ : 로그인 성공하면 홈으로
	//GET - /drive/folders/{UUID} : 조회
	//GET - /drive/folders/{UUID}/{fileName} : 폴더에서 file 클릭해서 다운로드 시작
	//POST - /drive/folders/{UUID} : 파일 업로드
	//POST - /drive/makefolder/{UUID} : 폴더 만들기
	//DELETE - /drive/folders/{UUID}/{fileName} : 파일, 폴더 삭제. 단, filename 은 비어있을 수 있게 처리. @RequestParam(required=false, defaultValue="")
}
