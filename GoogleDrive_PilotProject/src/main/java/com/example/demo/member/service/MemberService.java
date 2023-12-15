package com.example.demo.member.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.drive.repository.IDriveRepository;
import com.example.demo.drive.service.IDriveService;
import com.example.demo.member.model.Member;
import com.example.demo.member.repository.IMemberRepository;

@Service
public class MemberService implements IMemberService{
	
	@Autowired
	IMemberRepository memberRepository;
	
	@Autowired
	IDriveRepository driveRepository;
	
	@Autowired
	IDriveService driveService;
	
	@Transactional
	@Override
	public void regMember(Member member) {
		memberRepository.regMember(member);
		memberRepository.regMemberRole(member.getMemberId());
		driveService.makeFolderForNewMember(member.getMemberId()); //회원 가입하면, main Folder Local 에 생성하기
		//driveDao.makeFolder(member.getMemberId(), 0);
	}

	@Override
	public void updateMember(Member member) {
		memberRepository.updateMember(member);
	}
	
	@Override
	public boolean isMemberIdAuthenticForDrive(String memberId, String dirId) {
		System.out.println("멤버서비스실행됨");
		return memberId.equals(driveRepository.getMemberIdByDirId(dirId));
	}
	

	@Transactional
	@Override
	public void deleteMember(Member member) {
//		memberRepository.deleteMemberRole(member);
//		memberRepository.deleteMember(member);

	}
	
	@Override
	public String getPassword(String userid) {
//		return memberRepository.getPassword(userid);
		return "";
	}
	
	@Override
	public Member showMemberInfo(String memberid) {
		return memberRepository.showMemberInfo(memberid);
	}

	@Override
	public List<String> getRoles(String memberid) {
		return memberRepository.getRoles(memberid);
	}

	

	

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


//	@Override
//	public String setGrantMember(int memberId, String dirId) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
