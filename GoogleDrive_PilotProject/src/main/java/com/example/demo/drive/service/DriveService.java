package com.example.demo.drive.service;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.drive.controller.DriveController;
import com.example.demo.drive.model.DirPath;
import com.example.demo.drive.repository.DriveRepositoryForFile;
import com.example.demo.drive.repository.IDriveRepository;
import com.example.demo.member.repository.IMemberRepository;

@Service
public class DriveService implements IDriveService{
	
	private static final Logger logger = LoggerFactory.getLogger(DriveController.class);
	
	DriveRepositoryForFile driveRepositoryForFile = new DriveRepositoryForFile();
	
	@Autowired
	IDriveRepository driveRepository;
	
	@Autowired
	IMemberRepository memberRepository;
	

	@Override
	public String getDirPath(String dirId) {
		return driveRepository.getDirPath(dirId);
	}

	@Override
	public List<Map<String, Object>> getHomeOfMember(String memberid) {
		return driveRepository.getHomeOfMember(memberid);
	}

	@Override
	public List<Map<String, Object>> getSubDirectory(String dirId, String memberId) {
		//dirId 를 가지고 가서, 해당 dirId 의 memberId 를 들고 와야 한다.
		String dbMemberId = memberRepository.getMemberIdByDirId(dirId);
		
		if (dbMemberId.equals(memberId)) {
			//db에서 들고온 memberId 와 principal 에서 꺼낸 memberId 가 같으면, getSubDirectory 를 실행한다.
			
			//그 전에, 폴더인지 파일인지 확인을 해서 폴더면 getSub 를 하고, 파일이면 안내 메시지를 반환한다. 
			String isFolder = driveRepository.isFolder(dirId);
			if(isFolder.equals("TRUE")) {
				return driveRepository.getSubDirectory(dirId);
			} else {
				logger.info("========= getSubDirectory is denied because dirId was for file not folder =========");
			}
		}
		return new ArrayList<>(); //빈객체 반환
	}

	@Override
	public Resource downloadFile(String dirId) {
		//dirId 를 가지고 가서, DirPath 객체를 가져오자. 
		DirPath dirPathVO = driveRepository.getDirPathVo(dirId);
		String directory = dirPathVO.getDirectory();
		String fileName = dirPathVO.getFileName();
		return new ClassPathResource(directory+"//"+fileName);
	}
	
	@Override
	@Transactional
	public boolean uploadFile(String memberId, String parentDirId, MultipartFile multipartFile) {
		String parentDirPath = driveRepository.getDirectoryByDirId(parentDirId);
		if(!multipartFile.isEmpty()) {
			String originalFilename = multipartFile.getOriginalFilename();
			try {
				//해당 폴더의 경로에 file 을 생성
				multipartFile.transferTo(new File(parentDirPath+"//"+originalFilename)); 
				//DB에 파일정보 업로드
				//driveRepository.uploadFile(memberId, parentDirId, parentDirPath, originalFilename);
				String uuid = UUID.randomUUID().toString();
				java.util.Date date = new java.util.Date();
				Date nowDate = new Date(date.getTime());
				String contentType = multipartFile.getContentType();
				System.out.println(originalFilename);
				driveRepository.uploadFile(new DirPath(uuid, memberId, parentDirId, 
						parentDirPath, "FALSE", nowDate, nowDate, contentType, (int)multipartFile.getSize(), 
						originalFilename));
			} catch (IOException e) {
				System.out.println("로컬 경로에 파일 쓰기 실패");
				return false;
			} catch (IllegalStateException e) {
				System.out.println(
						" if the file has already been movedin the filesystem and is not available "
						+ "anymore for another transfer");
				return false;
			}
		}
		return true;
	}

	@Override
	@Transactional
	public boolean deleteFileCascade(String memberId, String parentDirId, String fileName) {
		try {
			//DB 데이터 삭제
			driveRepository.deleteFileCascade(memberId, parentDirId, fileName);
			//해당 파일 및 폴더가 존재하는 폴더 경로를 가져옴
			String parentDirPath = driveRepository.getDirectoryByDirId(parentDirId);
			//로컬 파일 삭제
			File file = new File(parentDirPath + "//" + fileName);
			file.delete();
		}catch (Exception e) { //로컬에서 실패했을 때
			System.out.println("로컬폴더에서 삭제 실패");
			return false;
		}
		return true;
	}

	@Override
	@Transactional
	public void makeFolder(String memberId, String parentDirId, String folderName) {
		//parentDirId를 기준으로 새로만들폴더의 상위폴더 경로를 꺼내옴
		String parentDirPath = driveRepository.getDirectoryByDirId(parentDirId); //기준경로
		System.out.println(parentDirPath);
		//"C:\dev\google_drive\parentDirPath\folderName"경로로 폴더를 생성
		createNewFolder(memberId, parentDirId, folderName, parentDirPath);
	}
	
	@Transactional
	@Override
	public void makeFolderForNewMember(String memberId) {
		String mainPath = "C:\\dev\\google_drive"; //초기 경로
		createNewFolder(memberId, "", String.valueOf(memberId), mainPath);
	}
	
	@Transactional
	public String createNewFolder(String memberId, String parentDirId, String folderName, String path){
		String uuid = UUID.randomUUID().toString();
		String newDirPath = path + "\\" + folderName;
		//DIR_PATH 테이블에 새로운 데이터 행 추가 
		java.util.Date date = new java.util.Date();
		Date nowDate = new Date(date.getTime());
		File Folder = new File(newDirPath);
		// 해당 디렉토리가 없을경우 디렉토리를 생성합니다.
		if (!Folder.exists()) {
			System.out.println(newDirPath);
			boolean isFolderCreated = driveRepository.makeFolder(new DirPath(uuid, memberId, parentDirId, newDirPath, "TRUE",
					nowDate, nowDate,"folder", 0, folderName)); 
			if(isFolderCreated) Folder.mkdir(); //폴더 생성합니다.
		    System.out.println("폴더가 생성되었습니다.");
		    return uuid;
		}else {
			System.out.println("동일이름의 폴더가 이미 존재합니다.");
			throw new RuntimeException();
		}
	}

	

//	@Override
//	public List<String> getHomeDirectory(int memberId) {
//		
//		return null; //level 1과 2 return
//	}
//
//	@Override
//	public List<String> getSubDirectory(String parentDirId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public MultipartFile downloadFile(String parentDirId) {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	@Override
//	public void uploadFile(int memberId, String parentDirId, MultipartFile multipartFile) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void deleteFileCascade(String parentDirId) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void makeFolder(int memberId, String parentDirId) {
//		// TODO Auto-generated method stub
//		
//	}

}
