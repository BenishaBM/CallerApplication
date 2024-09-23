package com.annular.callerApplication.model;

import java.time.LocalDateTime;

import org.bson.types.Binary;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "callerDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CallerDetails {

    @Id
    private String callerId; // MongoDB uses String type for ID

    @Field("start_time")
    private String callerStartTime;

    @Field("end_time")
    private String callerEndTime;

    @Field("duration_sec")
    private Integer callerDurationSec;

    @Field("total_hours")
    private Double totalHours;

    @Field("type")
    private String callerType;

    @Field("sender_number")
    private String senderNumber;

    @Field("receiver_number")
    private String receiverNumber;

    @Field("created_on")
    private LocalDateTime createdOn;

    @Field("updated_on")
    private LocalDateTime updatedOn;

    @Field("created_by")
    private Integer createdBy;

    @Field("updated_by")
    private Integer updatedBy;

    @Field("audio_file_path")
    private String audioFilePath; // New field for audio file path or URL
    
    @Field("is_Active")
    private Boolean isActive;
    
    @Field("notes")
    private String notes;
}
