package com.example.demo.member.controller;

import java.security.Principal;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.drive.controller.DriveController;
import com.example.demo.member.model.Member;
import com.example.demo.member.service.IMemberService;

import jakarta.servlet.http.HttpSession;

@RestController
public class MemberController {
	@Autowired
	IMemberService memberService;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
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
	
	//POST - /member/register : 회원 등록 시도
	@PostMapping(value="/member/register")
	public String regMember(@RequestBody Member member) {
		String encodedPw = passwordEncoder.encode(member.getPassword());
		member.setPassword(encodedPw);
		
		memberService.regMember(member);
		return "Member Register Success!";
	}
	
	
	//PUT - /member/update : 회원 정보 수정
	@PutMapping(value="/member/update")
	public String updateMember(HttpSession session) {
		System.out.println("session.getAttribute(\"password\") : " + session.getAttribute("password"));
		System.out.println("session.getAttribute(\"email\") : " + session.getAttribute("email"));
		
		//인코딩 해서 db 에 저장.
		memberService.updateMember((String)session.getAttribute("memberId"), 
				passwordEncoder.encode((String)session.getAttribute("password")),
				(String)session.getAttribute("email"));
		return "Member Update Success!";
	}
	
	//PUT - /member/update : 회원 정보 수정 테스트
	@PutMapping(value="/member/update/test")
	public String updateMemberTest(@RequestBody Map<String, String> MemberMap) {
		
		System.out.println("requestbody에서 password 꺼내기" + MemberMap.get("password"));
		System.out.println("requestbody에서 email 꺼내기" + MemberMap.get("email"));
		return "Member Update Test Success!";
	}
	
	@DeleteMapping(value="/member/delete")
	public String deleteMember(Principal principal) {
		memberService.deleteMember(principal.getName());
		return "Member Delete Success!";		
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
