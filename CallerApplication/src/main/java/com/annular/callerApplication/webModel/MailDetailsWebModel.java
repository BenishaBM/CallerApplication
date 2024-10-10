package com.annular.callerApplication.webModel;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDetailsWebModel {
	
	

	private String mailDetailsId;
	private String emailId;
	private Boolean isActive;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private Integer createdBy;
	private Integer updatedBy;


}
