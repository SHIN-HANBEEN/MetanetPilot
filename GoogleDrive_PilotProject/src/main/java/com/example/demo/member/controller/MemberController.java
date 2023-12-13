package com.example.demo.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.member.service.IMemberService;

@RestController
public class MemberController {
	@Autowired
	IMemberService memberService;
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
//GET - /member/setGrantMember : 다른 회원에게 폴더 공유 페이지
//POST - /member/setGrantMember : 다른 회원에게 폴더 공유
