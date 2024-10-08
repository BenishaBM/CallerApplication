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
public class MobileNumberResponse {
	
	private String mobileNumber;
	private String groupDetailsId;
	private String mobileNumberWithHypens;

}
