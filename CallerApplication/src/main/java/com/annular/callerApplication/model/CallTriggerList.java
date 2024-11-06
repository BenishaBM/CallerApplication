package com.annular.callerApplication.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "CallTriggerList")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallTriggerList {
	
	@Id
    private String triggerId; 
	@Field("senderId")              // Unique ID for the trigger
    private String senderId;  
	@Field("receiverId")         // ID of the sender
    private String receiverId; 
	@Field("status")
    private String status; 
	@Field("groupCode")// Status of the trigger (e.g., "initiated", "completed")
    private String groupCode; 
	@Field("triggerTime")// Group code (could be for grouping calls or a session identifier)
    private LocalDateTime triggerTime; // Timestamp of when the trigger occurred
}
