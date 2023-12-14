package com.example.demo.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.member.model.Member;

@Service
public class MemberService implements IMemberService{

	@Override
	public int regMember(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateMember(Member member) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int deleteMember(int memberId) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Member showMemberInfo(int memberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<String> getRoles(int memeberId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String setGrantMember(int memberId, String dirId) {
		// TODO Auto-generated method stub
		return null;
	}

}
