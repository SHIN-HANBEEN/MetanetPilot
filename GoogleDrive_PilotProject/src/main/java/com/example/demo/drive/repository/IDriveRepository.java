package com.example.demo.drive.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
@Mapper
@Repository
public interface IDriveRepository {

	String getFirstPathNByMemberId(int memberId);
	List<String> getNextPathByPrePath(int memberId, int parentDirId); //하위폴더의 폴더 및 파일 이름 확인
	MultipartFile downloadFile(int memberId, int parentDirId); //파일 다운
	void uploadFile(int memberId, int parentDirId, MultipartFile multipartFile);
	void deleteFileCascade(int meberId, int parentDirId); //트리거 설정 필요함
}
