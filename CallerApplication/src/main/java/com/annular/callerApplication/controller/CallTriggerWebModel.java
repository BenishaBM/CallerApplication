package com.annular.callerApplication.controller;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CallTriggerWebModel {

	private String triggerId; // Unique ID for the trigger
	private String senderId; // ID of the sender
	private String receiverId;
	private String status;
    private String groupCode;
    private LocalDateTime triggerTime;

}
