package com.example.demo.drive.repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.example.demo.drive.model.DirPath;

@Mapper
@Repository
public interface IDriveRepository {
//	String getFirstPathNByMemberId(int memberId);
//	List<String> getNextPathByPrePath(int memberId, String parentDirId); //하위폴더의 폴더 및 파일 이름 확인
//	MultipartFile downloadFile(int memberId, String parentDirId); //파일 다운
//	boolean uploadFile(int memberId, String parentDirId, MultipartFile multipartFile);
//	boolean deleteFileCascade(int meberId, String parentDirId); //CASCADE 설정 DB에서 함
//	boolean updateModifyDate(String dirId, Date date); // 파일 삭제, 업로드 하면, 수정 날짜 변경
//	boolean makeFolder(int memberId, String parentDirId); //폴더 만들기
	
//	HashMap<Integer, String> getHome(int memberId);
	
	String getDirPath(String dirId); //연습용
	List<Map<String, Object>> getHomeOfMember(String memberId); //user home  
	List<Map<String, Object>> getSubDirectory(String dirId);
	String isFolder(String dirId); //Folder 인지 확인하기
	DirPath getDirPathVo(String dirId); //dirPath 객체 가져오기
	
	boolean uploadFile(DirPath dirPath); //파일 업로드
	boolean deleteFileCascade(@Param("memberId") String meberId, @Param("parentDirId") String parentDirId, @Param("fileName") String fileName); //CASCADE 설정 DB에서 함
	boolean updateModifyDate(String dirId, Date date); // 파일 삭제, 업로드 하면, 수정 날짜 변경
	//폴더 생성
	boolean makeFolder(DirPath dirPath);
	//필요해서 새로 만듬 --완승
	String getDirectoryByDirId(@Param("parentDirId")String parentDirId); // 상위폴더의 파일의 경로를 가져오기
	
	String getMemberIdByDirId(String dirId);
}
