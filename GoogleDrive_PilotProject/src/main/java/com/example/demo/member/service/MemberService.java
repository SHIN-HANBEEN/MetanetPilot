package com.example.demo.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.member.model.Member;
import com.example.demo.member.repository.IMemberRepository;

@Service
public class MemberService implements IMemberService{
	
	@Autowired
	IMemberRepository memberRepository;

//	@Override
//	public int regMemberUser(Member member) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
	
//
//	@Override
//	public int updateMember(Member member) {
//		// TODO Auto-generated method stub
//		return 0;
//	}
//
//	@Override
//	public int deleteMember(int memberId) {
//		// TODO Auto-generated method stub
//		return 0;
//	}

	@Override
	public Member showMemberInfo(String memberid) {
		return memberRepository.showMemberInfo(memberid);
	}

	@Override
	public List<String> getRoles(String memberid) {
		return memberRepository.getRoles(memberid);
	}

//	@Override
//	public String setGrantMember(int memberId, String dirId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
