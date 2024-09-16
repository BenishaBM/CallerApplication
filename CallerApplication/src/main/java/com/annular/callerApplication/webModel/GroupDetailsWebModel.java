package com.annular.callerApplication.webModel;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetailsWebModel {

	private String gropuDetailsId;
	private String groupName;
	private String isActive;
	private List<String> mobileNumber;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private Integer createdBy;
	private Integer updatedBy;
	private String groupId;

}
