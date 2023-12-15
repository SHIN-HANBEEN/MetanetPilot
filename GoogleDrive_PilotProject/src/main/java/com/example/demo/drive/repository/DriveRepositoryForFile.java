package com.example.demo.drive.repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;

public class DriveRepositoryForFile implements IDriveRepositoryForFile{

	@Override
	public ResponseEntity<byte[]> sendFile(String path) {
		return null;
	}
	
//	public static MultipartFile getMockMultipartFile() throws IOException {
//        // Specify the path to the file
//        String filePath = "C:\\dev\\google-drive\\text.txt";
//
//        // Read the file content into a byte array
//        byte[] fileContent = Files.readAllBytes(Paths.get(filePath));
//
//        // Create a MockMultipartFile using the file content
//        MultipartFile multipartFile = new MockMultipartFile("text.txt", fileContent);
//
//        return multipartFile;
//    }
	
}
