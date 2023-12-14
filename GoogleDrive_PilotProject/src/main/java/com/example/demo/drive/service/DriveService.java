package com.example.demo.drive.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.drive.repository.IDriveRepository;

@Service
public class DriveService implements IDriveService{
	@Autowired
	IDriveRepository driveRepository;

	@Override
	public String getDirPath(String dirId) {
		return driveRepository.getDirPath(dirId);
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
