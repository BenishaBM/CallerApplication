package com.annular.callerApplication;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.annular.callerApplication.Security.UserDetailsImpl;

@Component
public class UserDetails {
	 public UserDetailsImpl userInfo() {
	        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
	        return (UserDetailsImpl) authentication.getPrincipal();
	    }


}
