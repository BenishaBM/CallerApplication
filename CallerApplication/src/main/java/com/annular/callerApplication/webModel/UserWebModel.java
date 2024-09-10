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
public class UserWebModel {
	private String emailId;
	private String userName;
	private Integer mobileNumber;
	private String userType;

	private Integer createdBy;
	private Integer updatedBy;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private Boolean isActive;
	private String password;
	private String gender;

}
