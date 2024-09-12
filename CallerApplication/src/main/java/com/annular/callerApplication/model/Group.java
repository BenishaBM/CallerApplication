package com.annular.callerApplication.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Group")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Group {
	
    @Id
    private String groupId;
    
    @Field("groupName")
    private String groupName;
   
    @Field("isActive")
    private String isActive;
    
    @Field("created_on")
    private LocalDateTime createdOn;

    @Field("updated_on")
    private LocalDateTime updatedOn;

    @Field("created_by")
    private Integer createdBy;

    @Field("updated_by")
    private Integer updatedBy;


}
