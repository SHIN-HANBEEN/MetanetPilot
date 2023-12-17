package com.example.demo.member.service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

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
	public void updateMember(String memberId, String encodedPw, String email) {
		memberRepository.updateMember(memberId, encodedPw, email);
	}
	
	@Override
	public boolean isMemberIdAuthenticForDrive(String memberId, String dirId) {
	    System.out.println("멤버서비스실행됨");
	    List<Map<String, String>> memberIdList = memberRepository.getCascadeMemberIdByDirIdInDirPathAndSharedTable(dirId);
	    Stream<String> flatMemberIdStream = memberIdList.stream()
	            .flatMap(map -> map.values().stream().distinct()); //flatMap으로 Stream<String> 을 만든 다음,
	    return flatMemberIdStream.anyMatch(id -> id.equals(memberId)); //anyMatch 로 일치하는 것이 있는지 확인하기
	}

	
	@Override
	public boolean isMemberIdAuthenticForMember(String principalMemberId, String inputMemberId) {
		return principalMemberId.equals(inputMemberId);
	}
	

	@Transactional
	@Override
	public void deleteMember(String memberId) {
//		memberRepository.deleteMemberRole(memberId); //delete cascade 걸어놔서 member 삭제 될 때 자동으로 삭제 된다.
//		driveRepository.deleteSharedTable(memberId);
//		String homeDirId = driveRepository.getHomeDirByMemberId(memberId);
//		driveRepository.deleteDirPathByHomeDirId(homeDirId); //delete cascade 를 걸어놓아서, 자기참조와, shared_table 자도 삭제 됨.
		memberRepository.deleteMember(memberId); //delete casecade 를 걸어놔서 member 만 삭제하면 이어서 다 삭제됨.

	}
	
	@Override
	public String getPasswordByMemberId(String memberId) {
		return memberRepository.getPasswordByMemberId(memberId);
	}
	
	@Override
	public Member showMemberInfo(String memberid) {
		return memberRepository.showMemberInfo(memberid);
	}

	@Override
	public List<String> getRoles(String memberid) {
		return memberRepository.getRoles(memberid);
	}

	@Override
	public void setGrantMember(String memberId, String dirId) {
		memberRepository.setGrantMember(memberId, dirId);
	}

	@Override
	public boolean isMemberIdAuthenticForGrantMember(String dirId, String inputMemberId) {
		return memberRepository.getMemberIdByDirIdInDirPath(dirId).equals(inputMemberId) || (memberRepository.getPasswordByMemberId(inputMemberId) != null);
		//MEMBER 테이블에 등록된 회원을, 특정 DIR_ID 에 대해서 권한을 주기 위해, 등록되어있는지, DIR_ID 에 대한 '주인'일 때만 가능하도록 반환.
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
