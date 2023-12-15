package com.example.demo.member.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.drive.controller.DriveController;
import com.example.demo.member.service.IMemberService;

@RestController
public class MemberController {
	@Autowired
	IMemberService memberService;
	
	private static final Logger logger = LoggerFactory.getLogger(DriveController.class);
	
	@GetMapping("/member/login")
	public String getMemberLogin() {
		logger.info("============ /member/login Get activated ================");
		return "member/login";
	}
	
	@GetMapping("/member/loginsuccess")
	public String loginSuccess() {
		logger.info("============ /member/login Get activated ================");
		return "LOGIN SUCCESS";
	}
	
	@GetMapping(value="/member/logout")
	public String getMemberLogout() {
		return "member/logout";
	}
	
}

//GET - /member/login : Security 인증 바로 시작 
//POST - /member/login : 로그인 시도
//GET - /member/register : 회원 등록 페이지
//POST - /member/register : 회원 등록 시도
//GET - /member/update : 회원 정보 수정 페이지
//PUT - /member/update : 회원 정보 수정
//GET - /member/delete : 회원 삭제 페이지
//DELETE - /member/delete : 회원 삭제
//GET - /member/showinfo : 회원 정보 조회
//POST - /member/setgrantmember/{UUID}/{memberid} : 다른 회원에게 폴더 공유
