package com.annular.callerApplication.controller;

import java.util.Optional;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.annular.callerApplication.Response;
import com.annular.callerApplication.UserStatusConfig;
import com.annular.callerApplication.Security.UserDetailsImpl;
import com.annular.callerApplication.Security.jwt.JwtResponse;
import com.annular.callerApplication.Security.jwt.JwtUtils;
import com.annular.callerApplication.Service.AuthenticationService;
import com.annular.callerApplication.model.RefreshToken;
import com.annular.callerApplication.model.User;
import com.annular.callerApplication.repository.UserRepository;
import com.annular.callerApplication.webModel.UserWebModel;



@RestController
@RequestMapping("/user")
public class AuthController {
	
	public static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserStatusConfig loginConstants;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	JwtUtils jwtUtils;
	
//    @Autowired
//    UserService userServices;
	
	 @PostMapping("login")
	    public ResponseEntity<?> login(@RequestBody UserWebModel userWebModel) {
	        try {
	            Optional<User> checkUsername = userRepository.findByEmailId(userWebModel.getEmailId());
	            if (checkUsername.isPresent()) {
	                loginConstants.setUserType(userWebModel.getUserType());
	                logger.info("In login() User type from constants -> {}", loginConstants.getUserType());

	                Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userWebModel.getEmailId(), userWebModel.getPassword()));
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	                RefreshToken refreshToken = userService.createRefreshToken(userWebModel);

	                User user = checkUsername.get();

	                String jwt = jwtUtils.generateJwtToken(authentication);
	                UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
	                logger.info("Login Controller ---- Finished");
	                return ResponseEntity.ok(new JwtResponse(jwt,
	                        userDetails.getId(),
	                        userDetails.getUsername(),
	                        userDetails.getEmail(),

	                        1,
	                        refreshToken.getToken(),
	                        userDetails.getUserType()
	                        ));
	            }
	        } catch (Exception e) {
	            logger.error("Error at login() -> {}", e.getMessage());
	            e.printStackTrace();
	            return ResponseEntity.internalServerError().body(new Response(-1, "Error while validating the user credentials. Please try again...", null));
	        }
	        return ResponseEntity.badRequest().body(new Response(-1, "Invalid EmailId", ""));
	    }

	    @PostMapping("register")
	    public ResponseEntity<?> userRegister(@RequestBody UserWebModel userWebModel, String request) {
	        try {
	            logger.info("User details to register :- {}", userWebModel);
	            return userService.register(userWebModel, request);
	        } catch (Exception e) {
	            logger.error("userRegister Method Exception -> {}", e.getMessage());
	            e.printStackTrace();
	        }
	        return ResponseEntity.ok(new Response(-1, "Fail", ""));
	    }

}
