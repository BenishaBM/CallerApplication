package com.annular.callerApplication.model;

import java.time.LocalTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "refreshTokens")
@Builder
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class RefreshToken {

    @Id
    private String id; // MongoDB uses String type for IDs

    @Field(name = "token")
    private String token;

    @Field(name = "expiry_token")
    private LocalTime expiryToken;

    @Field(name = "user_id")
    private Integer userId;
}
