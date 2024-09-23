package com.annular.callerApplication.Service.ServiceImpl;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.annular.callerApplication.Response;
import com.annular.callerApplication.Service.AuthenticationService;
import com.annular.callerApplication.Util.Utility;
import com.annular.callerApplication.model.RefreshToken;
import com.annular.callerApplication.model.User;
import com.annular.callerApplication.repository.RefreshTokenRepository;
import com.annular.callerApplication.repository.UserRepository;
import com.annular.callerApplication.webModel.UserWebModel;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	public static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RefreshTokenRepository refreshTokenRepository;

	@Override
	public RefreshToken createRefreshToken(UserWebModel userWebModel) {
		try {
			logger.info("createRefreshToken method start");
			Optional<User> data = userRepository.findByEmailId(userWebModel.getEmailId());
//	            if (data.isPresent()) {
//	                Optional<RefreshToken> refreshTokenData = refreshTokenRepository.findByUserId(data.get().getUserId());
//	                refreshTokenData.ifPresent(token -> refreshTokenRepository.delete(token));

			RefreshToken refreshToken = RefreshToken.builder().build();
			refreshToken.setId(data.map(User::getId).orElse(null));
			refreshToken.setToken(UUID.randomUUID().toString());
//	                refreshToken.setExpiryToken(LocalTime.now().plusMinutes(45));
			refreshToken = refreshTokenRepository.save(refreshToken);

			logger.info("createRefreshToken method end");
			return refreshToken;
//	            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public ResponseEntity<?> register(UserWebModel userWebModel, String request) {
		HashMap<String, Object> response = new HashMap<>();
		try {
			logger.info("Register method start");
			Optional<User> userData = userRepository.findByEmailIdAndUserType(userWebModel.getEmailId(),
					userWebModel.getUserType());
			if (userData.isEmpty()) {
				User user = new User();
				user.setMobileNumber(userWebModel.getMobileNumber());

				StringBuilder name = new StringBuilder();
				if (!Utility.isNullOrBlankWithTrim(userWebModel.getUserName())) {
					user.setUserName(userWebModel.getUserName());

				}

				user.setEmailId(userWebModel.getEmailId());
				user.setUserType(userWebModel.getUserType());
				user.setIsActive(true);
				user.setGender(userWebModel.getGender());

				String encryptPwd = new BCryptPasswordEncoder().encode(userWebModel.getPassword());
				user.setPassword(encryptPwd);

				user = userRepository.save(user);
				response.put("userDetails", user);
				// response.put("verificationCode", user.getVerificationCode());
			} else {
				return ResponseEntity.unprocessableEntity().body(new Response(1, "This Account already exists", ""));
			}
			logger.info("Register method end");
		} catch (Exception e) {
			logger.error("Register Method Exception -> {}", e.getMessage());
			e.printStackTrace();
			return ResponseEntity.internalServerError()
					.body(new Response(-1, "Failed to register the user. Try Again...", e.getMessage()));
		}
		return ResponseEntity.ok()
				.body(new Response(1, "User was registered  successfully...", response));
	}

	@Override
	public Response verifyExpiration(RefreshToken refreshToken) {
		// TODO Auto-generated method stub
	    return new Response(-1, "RefreshToken expired", "");
	    }
	}


