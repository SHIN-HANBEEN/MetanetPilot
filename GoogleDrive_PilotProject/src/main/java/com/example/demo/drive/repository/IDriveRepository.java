package com.example.demo.drive.repository;

import java.sql.Date;
import java.util.List;

import javax.swing.text.StyledEditorKit.BoldAction;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;
@Mapper
@Repository
public interface IDriveRepository {

	String getFirstPathNByMemberId(int memberId);
	List<String> getNextPathByPrePath(int memberId, int parentDirId); //하위폴더의 폴더 및 파일 이름 확인
	MultipartFile downloadFile(int memberId, int parentDirId); //파일 다운
	boolean uploadFile(int memberId, int parentDirId, MultipartFile multipartFile);
	boolean deleteFileCascade(int meberId, int parentDirId); //CASCADE 설정 DB에서 함
	boolean updateModifyDate(String dirId, Date date); // 파일 삭제, 업로드 하면, 수정 날짜 변경
	boolean makeFolder(int memberId, int parentDirId); //폴더 만들기
}
