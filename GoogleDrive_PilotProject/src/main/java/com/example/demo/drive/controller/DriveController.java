package com.example.demo.drive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.drive.service.IDriveService;

@RestController
public class DriveController {
	@Autowired
	IDriveService driveService;
	
	//GET - /drive/home/
	//GET - /dirve/{memberId}/
}
