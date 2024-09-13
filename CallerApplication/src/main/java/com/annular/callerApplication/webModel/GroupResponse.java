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
    private String groupStatus;
    private List<MobileNumberResponse> mobileNumbers;

}
