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
public class MailDetailsWebModel {
	
	

	private String mailDetailsId;
	private String emailId;
	private Boolean isActive;
	private LocalDateTime createdOn;
	private LocalDateTime updatedOn;
	private Integer createdBy;
	private Integer updatedBy;
	private QuotationDataWebModel quotationData; // Changed to use the nested model
	private String addressDetails;
	private String nameData;
	private String phoneNumber;
	private String emailSummary;


}
