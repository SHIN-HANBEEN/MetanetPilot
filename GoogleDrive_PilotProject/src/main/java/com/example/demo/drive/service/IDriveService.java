package com.example.demo.drive.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IDriveService {
//	List<String> getHomeDirectory(int memberId); //유저 구글 드라이브 홈 - 한빈
//	List<String> getSubDirectory(String parentDirId); //하위폴더의 폴더 및 파일 이름 확인 - 한빈
//	MultipartFile downloadFile(String parentDirId); //파일 다운 -한빈
//	
//	/* 파일을 upload 랑 delete 하면, 해당 폴더의 수정 날짜 변경되어야함 */
//	/* 파일 업로드랑 삭제할 때, 인가 확인해야함 */
//	void uploadFile(int memberId, String parentDirId, MultipartFile multipartFile); // -완승
//	void deleteFileCascade(String parentDirId); //트리거 설정 필요함 // -완승
//	void makeFolder(int memberId, String parentDirId); //폴더 만들기 // -완승
	
	String getDirPath(String dirId);
}
