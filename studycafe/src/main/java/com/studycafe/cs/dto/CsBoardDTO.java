package com.studycafe.cs.dto;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.domain.Page;

import com.studycafe.cs.entity.CsEntity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class CsBoardDTO implements Serializable{
	
	
	/**
	 * @serial CsBoardDTO
	 */
	private static final long serialVersionUID = 3138806864541130044L;
	
	// Data Transferation to TeamBoardEntity
	private long idx;
	
	@NotBlank(message = "제목을 반드시 입력해주세요.")
	private String csTitle;
	
	@Size(min = 12, max = 500 , message = "12자 이상 500자 이하의 내용만 가능합니다.")
	private String csContent;
	private String csWriter;
	private String username;
	private String filePath;
	private String fileKey;
	private boolean secret;
	private Timestamp createDate;
	private LocalDateTime modifiedDate;

	// 단일 dto->entity
	public CsEntity sendDataToEntity() {

		CsEntity csEntityBuilder = CsEntity.builder()
				.idx(idx)
				.csTitle(csTitle)
				.csContent(csContent)
				.csWriter(csWriter)
				.filePath(filePath)
				.fileKey(fileKey)
				.secret(secret)
				.username(username)
				.createDate(createDate)
				.modifiedDate(modifiedDate)
				.build();

		return csEntityBuilder;
	}

	// 단일 entity->dto
	public static CsBoardDTO sendDataToDto(CsEntity csEntity) {

		CsBoardDTO csDtoBuilder = CsBoardDTO.builder()
				.idx(csEntity.getIdx())
				.csTitle(csEntity.getCsTitle())
				.csContent(csEntity.getCsContent())
				.csWriter(csEntity.getCsWriter())
				.filePath(csEntity.getFilePath())
				.fileKey(csEntity.getFileKey())
				.secret(csEntity.isSecret())
				.username(csEntity.getUsername())
				.createDate(csEntity.getCreateDate())
				.modifiedDate(csEntity.getModifiedDate())
				.build();

		return csDtoBuilder;
	}

	// 페이징 객체 DTO 변환작업
	public Page<CsBoardDTO> toDtoList(Page<CsEntity> boardList) {

		Page<CsBoardDTO> csDtoList = boardList.map(list -> CsBoardDTO.builder()
				.idx(list.getIdx())
				.csTitle(list.getCsTitle())
				.csContent(list.getCsContent())
				.csWriter(list.getCsWriter())
				.username(list.getUsername())
				.fileKey(list.getFileKey())
				.secret(list.isSecret())
				.createDate(list.getCreateDate())
				.build());

		return csDtoList;

	}

	@Builder
	public CsBoardDTO(
			long idx, String csTitle,
			String csContent, String csWriter,
			Timestamp createDate, LocalDateTime modifiedDate,
			String filePath, String fileKey,
			boolean secret, String username) {

		this.idx = idx;
		this.csTitle = csTitle;
		this.csContent = csContent;
		this.csWriter = csWriter;
		this.username = username;
		this.filePath = filePath;
		this.fileKey = fileKey;
		this.secret = secret;
		this.createDate = createDate;
		this.modifiedDate = modifiedDate;
	}

}
