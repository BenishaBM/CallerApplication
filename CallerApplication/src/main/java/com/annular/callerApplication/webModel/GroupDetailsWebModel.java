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
	private Boolean isActive;
	private List<MobileNumberResponse> mobileNumber;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private Integer createdBy;
	private Integer updatedBy;
	private String groupId;
    private String groupCode;
    private String groupName;
}
