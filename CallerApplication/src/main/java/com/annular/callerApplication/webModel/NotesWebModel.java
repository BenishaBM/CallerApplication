package com.annular.callerApplication.webModel;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotesWebModel {
	

	private String notesHistoryId;
	private String receiverNumber;
	private String senderNumber;
	private String isActive;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private Integer createdBy;
	private Integer updatedBy;
	private String notes;
	private String groupCode;
	private String emailId;
	private String userName;

}
