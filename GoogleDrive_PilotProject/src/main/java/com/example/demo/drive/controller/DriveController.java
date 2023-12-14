package com.example.demo.drive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.drive.service.IDriveService;

@RestController
public class DriveController {
	@Autowired
	IDriveService driveService;
	
	
	

	//GET - /drive/home/ : 로그인 성공하면 홈으로
	//GET - /drive/folders/{UUID} : 조회
	//GET - /drive/folders/{UUID}/{fileName} : 폴더에서 file 클릭해서 다운로드 시작
	//POST - /drive/folders/{UUID} : 파일 업로드
	//POST - /drive/makeFolder/{UUID} : 폴더 만들기
	//DELETE - /drive/folders/{UUID}/{fileName} : 파일, 폴더 삭제. 단, filename 은 비어있을 수 있게 처리. @RequestParam(required=false, defaultValue="")
}
