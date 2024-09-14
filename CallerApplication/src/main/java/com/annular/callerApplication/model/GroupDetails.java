package com.annular.callerApplication.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.ElementCollection;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "GroupDetails")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDetails {
	
    @Id
    private String groupDetailsId;
    
    @Field
    private String mobileNumbers;
   
    @Field("isActive")
    private String isActive;
    
    @Field("groupId")
    private String groupId;
    
    @Field("created_on")
    private LocalDateTime createdOn;

    @Field("updated_on")
    private LocalDateTime updatedOn;

    @Field("created_by")
    private Integer createdBy;

    @Field("updated_by")
    private Integer updatedBy;
    
}
