package com.annular.callerApplication.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "notesHistory")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotesHistory {

	@Id
	private String notesHistoryId;

	@Field("receiverNumber")
	private String receiverNumber;

	@Field("senderNumber")
	private String senderNumber;

	@Field("isActive")
	private String isActive;

	@Field("created_on")
	private LocalDateTime createdOn;

	@Field("updated_on")
	private LocalDateTime updatedOn;

	@Field("created_by")
	private Integer createdBy;

	@Field("updated_by")
	private Integer updatedBy;

	@Field("notes")
	private String notes;
	
	@Field("groupCode")
	private String groupCode;
	
	@Field("emailId")
	private String emailId;
	
	@Field("userName")
	private String userName;
	
}
