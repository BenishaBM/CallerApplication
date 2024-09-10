package com.annular.callerApplication.webModel;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallerDetailsWebModel {


	    private Integer callerDurationSec;
	    private Double totalHours;
	    private String callerEndTime;

	    private String callerType;
	    private String callerStartTime;
	    private String senderNumber;
	    private String receiverNumber;
	    private LocalDateTime createdOn;
	    private LocalDateTime updatedOn;
	    private Integer createdBy;
	    private Integer updatedBy;
	    private MultipartFile audioFileData;  // If you're uploading audio files
	    private Boolean isActive;

}
