package com.annular.callerApplication.Service;

import org.springframework.http.ResponseEntity;

import com.annular.callerApplication.model.RefreshToken;
import com.annular.callerApplication.webModel.UserWebModel;

public interface AuthenticationService {

	RefreshToken createRefreshToken(UserWebModel userWebModel);

	ResponseEntity<?> register(UserWebModel userWebModel, String request);

}
