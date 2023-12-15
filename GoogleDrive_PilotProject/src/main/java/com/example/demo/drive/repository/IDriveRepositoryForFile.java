package com.example.demo.drive.repository;

import org.springframework.http.ResponseEntity;

public interface IDriveRepositoryForFile {
	ResponseEntity<byte[]> sendFile(String path);
}
