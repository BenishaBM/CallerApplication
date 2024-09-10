package com.annular.callerApplication.Security.jwt;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class JwtResponse {

    private String jwt;
    private String id;
    private String username;
    private String email;
    private Integer status;
    private String token;
    private String userType;


}
