package com.annular.callerApplication.webModel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {
	private String groupName;
	private String groupId;
    private Boolean groupStatus;
    private List<MobileNumberResponse> mobileNumbers;
    private String groupCode;
	public  String mobileNumber;
	

		
		
	}


