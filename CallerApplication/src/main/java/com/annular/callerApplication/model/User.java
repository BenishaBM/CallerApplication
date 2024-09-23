package com.annular.callerApplication.model;

import java.time.LocalDateTime;


import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "user")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @Field(name = "id")
    private String id; // MongoDB uses String type for ID

    @Field(name = "email_id")
    private String emailId;

    @Field(name = "user_name")
    private String userName;

    @Field(name = "mobile_number")
    private Integer mobileNumber;

    @Field(name = "user_type")
    private String userType;

    @Field(name = "password")
    private String password;

    @Field(name = "created_by")
    private Integer createdBy;

    @Field(name = "updated_by")
    private Integer updatedBy;

    @Field(name = "created_on")
    private LocalDateTime createdOn;

    @Field(name = "updated_on")
    private LocalDateTime updatedOn;

    @Field(name = "is_active")
    private Boolean isActive;
    
    @Field(name = "gender")
    private String gender;
}
