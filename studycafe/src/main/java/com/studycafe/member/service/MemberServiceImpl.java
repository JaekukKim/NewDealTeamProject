package com.studycafe.member.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.studycafe.member.dto.MemberDto;
import com.studycafe.member.dto.MemberMapper;
import com.studycafe.member.dto.MemberSafeDto;
import com.studycafe.member.entity.MemberAddressEntity;
import com.studycafe.member.entity.MemberEntity;
import com.studycafe.member.repository.MemberAddressRepository;
import com.studycafe.member.repository.MemberRepository;
import com.studycafe.team.entity.TeamEntity;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

	@Autowired
	private MemberRepository memRe;

	@Autowired
	private MemberAddressRepository memberAddRe;

	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	private MemberMapper memberMapper;

	@Override
	@Transactional
	public boolean insertMember(MemberEntity memberEntity, MemberAddressEntity memberAddressEntity) {

		MemberEntity insert1 = memRe.save(memberEntity);

		try {
			log.info("insert1 : {}", insert1);
			if (insert1 != null) {
				try {
					memberAddressEntity.setMemberEntity(memberEntity);
					MemberAddressEntity insert2 = memberAddRe.save(memberAddressEntity);
					log.info("insert2 : {}", insert2);
					if (insert2 != null) {
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		return false;
	}

	@Override
	@Transactional
	public boolean idCheck(String username) {

		try {
			boolean idCheck = memRe.existsByUsername(username);
			log.info("idCheck 의 값은 ? :{}", idCheck);
			if (idCheck == true) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;

	}

	@Override
	@Transactional
	public boolean nickCheck(String nickName) {

		try {
			boolean nickCheck = memRe.existsByNickName(nickName);
			if (nickCheck == true) {
				return true;
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return false;
	}

	@Override
	public boolean emailCheck(String email) {
		try {
			boolean emailCheck = memRe.existsByEmail(email);
			if (emailCheck) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public int getNewMemberCount() {

		return memRe.findNewUser();
	}

	@Override
	public List<MemberSafeDto> getAllMember() {
		List<MemberEntity> mem = memRe.findAll();
		return mem.stream().map(memberMapper::memberSafeDto).collect(Collectors.toList());
	}

	@Override
	public MemberEntity getUsername(String email) {
		try {
			boolean getUsername = memRe.existsByEmail(email);
			if (getUsername == true) {
				MemberEntity memberEntity = memRe.findByEmail(email);
				return memberEntity;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MemberEntity findUsername(String username) {
		try {
			boolean findUsername = memRe.existsByUsername(username);
			if (!findUsername) {
				return null;
			}
			MemberEntity memberEntity = memRe.findByUsername(username);
			return memberEntity;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public MemberEntity findUsernameByEmail(String username, String email) {
		try {
			MemberEntity memberEntity = memRe.findByUsernameAndEmail(username, email);
			if (memberEntity == null) {
				return null;
			}
			return memberEntity;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updatePassword(String username, String password) {

		try {
			MemberEntity memberEntity = memRe.findByUsername(username);

			if (memberEntity == null) {
				return false;
			}

			boolean passwordMatches = encoder.matches(password, memberEntity.getPassword());

			if (!passwordMatches) {

				memberEntity.setPassword(encoder.encode(password));
				memRe.save(memberEntity);
				return true;
			} else {

				return false;
			}

		} catch (

		Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	// 회원정보 수정
	@Override
	public MemberAddressEntity getUserAddress(String username) {

		return memberAddRe.findByMemberEntity_Username(username);
	}

	@Override
	@Transactional
	public boolean updateInfo(MemberDto memberDto) {

		String username = memberDto.getUsername();

		MemberEntity memberInfo = memRe.findByUsername(username);

		if (memberInfo == null) {
			throw new IllegalArgumentException("회원의 정보가 일치하지 않습니다.");
		}

		memberInfo.setNickName(memberDto.getNickName());

		MemberAddressEntity memberAddressInfo = memberAddRe.findByMemberEntity_Username(username);

		//
		memberAddressInfo.setZipcode(memberDto.getZipcode());
		memberAddressInfo.setAddress1(memberDto.getAddress1());
		memberAddressInfo.setAddress2(memberDto.getAddress2());
		//

		try {
			MemberAddressEntity result1 = memberAddRe.save(memberAddressInfo);

			if (result1 == null) {
				return false;
			}

			return true;

		} catch (Exception e) {
			e.printStackTrace();

		}

		try {
			MemberEntity result2 = memRe.save(memberInfo);

			if (result2 == null) {
				return false;
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	// 유저정보 불러오기
	@Override
	public MemberEntity getMember(String userName) {
		// TODO Auto-generated method stub

		MemberEntity memberEn = memRe.findByNickName(userName);

		return memberEn;
	}

	// 카카오닉네임첵
	@Override
	public int checkNick(String nickName) {
		// TODO Auto-generated method stub
		return memRe.checkNick(nickName);
	}

	// 카카오 억지가입
	@Override
	public void insertKaKao(MemberEntity memberEntity) {
		// TODO Auto-generated method stub
		memRe.save(memberEntity);
	}

	@Override
	public boolean checkPassword(String username, String oneraepassword) {
		MemberEntity memberEntity = findUsername(username);
		try {
			boolean passwordMatches1 = encoder.matches(oneraepassword, memberEntity.getPassword());
			if (!passwordMatches1) {
				return false;
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean changePassword(String username, String password) {

		MemberEntity memberEntity = findUsername(username);

		try {

			boolean passwordMatches2 = encoder.matches(password, memberEntity.getPassword());
			if (passwordMatches2) {
				return false;
			}
			memberEntity.setPassword(encoder.encode(password));
			memRe.save(memberEntity);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<MemberEntity> getMyTeamMember(TeamEntity teamNumber) {

		List<MemberEntity> member = memRe.findByTeamNumber(teamNumber);
		return member;
	}

	public List<MemberSafeDto> searchMember(String username) {
		// TODO Auto-generated method stub
		List<MemberEntity> mem = memRe.findByUsernameContainingIgnoreCase(username);
		return mem.stream().map(memberMapper::memberSafeDto).collect(Collectors.toList());
	}

	@Override
	public boolean updateTeamInfo(String members, TeamEntity teamEntity) {
		String[] mems = members.split(",");

		try {
			for (String mem : mems) {
				MemberEntity member = memRe.findByUsername(mem.trim());
				if (member != null)
					member.setTeamNumber(teamEntity);
				memRe.saveAndFlush(member);
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	@Override
	public MemberEntity getOutTeam(String username, long teamNumber) {
		MemberEntity member = memRe.findByUsernameAndTeamNumberTeamNumber(username, teamNumber);
		return member;
	}

	@Override
	public void getOutTeamSave(MemberEntity memberEntity) {
		memRe.save(memberEntity);
	}

}