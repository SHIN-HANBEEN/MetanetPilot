package com.example.demo.drive.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface IDriveService {
	String getFirstPathNByMemberId();
	List<String> getNextPathByPrePath(int parentDirId); //하위폴더의 폴더 및 파일 이름 확인
	MultipartFile downloadFile(int parentDirId); //파일 다운
	void uploadFile(int memberId, int parentDirId, MultipartFile multipartFile);
	void deleteFileCascade(int parentDirId); //트리거 설정 필요함
	List<String> getRoles();

}
