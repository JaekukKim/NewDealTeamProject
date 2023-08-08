package com.studycafe.team.teamboard.dto;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.domain.Page;

import com.studycafe.team.entity.TeamEntity;
import com.studycafe.team.teamboard.entity.TeamBoardEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeamBoardDTO {
	// Data Transferation to TeamBoardEntity

	private long teamBoardNum;
	
	@NotBlank(message = "제목을 반드시 입력해주세요.")
	private String teamBoardTitle;
	
	@Size(min = 12, max = 500 , message = "12자 이상 500자 이하의 내용만 가능합니다.")
	private String teamBoardContent;
	private String teamBoardWriter;
	@NotBlank(message = "팀이름을 먼저 등록해주세요.")
	private String teamName;
	@NotBlank(message = "팀멤버를 설정해주세요.")
	private String teamMember;
	private Timestamp createDate;
	private LocalDateTime modifiedDate;

	// 단일 dto 리턴
	public TeamBoardEntity sendDataToEntity() {

		TeamBoardEntity teamBoardEntityBuilder = TeamBoardEntity.builder()
				.teamBoardNum(teamBoardNum)
				.teamBoardTitle(teamBoardTitle)
				.teamBoardContent(teamBoardContent)
				.teamBoardWriter(teamBoardWriter)
				.teamName(teamName)
				.teamMember(teamMember)
				.modifiedDate(modifiedDate)
				.build();

		return teamBoardEntityBuilder;
	}
	
	public TeamEntity toTeamEntity() {
		int count = teamMember.split(",").length;
		TeamEntity teamEntity = TeamEntity.builder()
				.teamHead(teamBoardWriter)
				.teamName(teamName)
				.memberCount(count)
				.build();
		return teamEntity;
	}


	// 페이징 객체 DTO 변환작업
	public Page<TeamBoardDTO> toDtoList(Page<TeamBoardEntity> boardList) {

		Page<TeamBoardDTO> boardDtoList = boardList.map(list -> TeamBoardDTO.builder()
				.teamBoardNum(list.getTeamBoardNum())
				.teamBoardTitle(list.getTeamBoardTitle())
				.teamBoardWriter(list.getTeamBoardWriter())
				.teamBoardContent(list.getTeamBoardContent())
				.createDate(list.getCreateDate())
				.build());

		return boardDtoList;

	}

	@Builder
	public TeamBoardDTO(long teamBoardNum, String teamBoardTitle, String teamBoardContent, String teamBoardWriter,
			String teamName, String teamMember, Timestamp createDate, LocalDateTime modifiedDate) {
		this.teamBoardNum = teamBoardNum;
		this.teamBoardTitle = teamBoardTitle;
		this.teamBoardContent = teamBoardContent;
		this.teamBoardWriter = teamBoardWriter;
		this.teamName = teamName;
		this.teamMember = teamMember;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
	}

}
