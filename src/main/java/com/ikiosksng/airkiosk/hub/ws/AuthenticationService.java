package com.ikiosksng.airkiosk.hub.ws;

import java.util.Random;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.ikioskng.airkiosks.ws.types.ErrorOrWarning;
import com.ikioskng.airkiosks.ws.types.LoginRequest;
import com.ikioskng.airkiosks.ws.types.LoginResponse;
import com.ikioskng.airkiosks.ws.types.LogoutRequest;
import com.ikioskng.airkiosks.ws.types.LogoutResponse;
import com.ikioskng.airkiosks.ws.types.RegisterUserRequest;
import com.ikioskng.airkiosks.ws.types.RegisterUserResponse;
import com.ikioskng.airkiosks.ws.types.UpdateProfileRequest;
import com.ikioskng.airkiosks.ws.types.UpdateProfileResponse;

@Endpoint
public class AuthenticationService {

	
	@PayloadRoot(namespace=ServiceConstants.NAME_SPACE_URI,localPart="login-request")
	public @ResponsePayload LoginResponse login(@RequestPayload LoginRequest loginRequest) {
		LoginResponse response = new LoginResponse();
		
		if (loginRequest.getPassword().equals("welcome123")) {
			response.setSuccess(Boolean.TRUE);
			response.setSessionId("sesion-id" + new Random().nextInt());
		} else {
			response.setSuccess(false);
			ErrorOrWarning loginFailed = new ErrorOrWarning();
			loginFailed.setCode("LOGIN_FAILED");
			loginFailed.setMessage("Invalid user name or password");
			response.getError().add(loginFailed);
		}
		
		return response;
	}

	@PayloadRoot(namespace=ServiceConstants.NAME_SPACE_URI,localPart="logout-request")
	public @ResponsePayload LogoutResponse logout(@RequestPayload LogoutRequest logoutRequest) {
		LogoutResponse response = new LogoutResponse();
		response.setSuccess(true);
		return response;
	}
	
	@PayloadRoot(namespace=ServiceConstants.NAME_SPACE_URI,localPart="register-user-request")
	public @ResponsePayload RegisterUserResponse register(@RequestPayload RegisterUserRequest registerUserRequest) {
		RegisterUserResponse response = new RegisterUserResponse();
		
		if (!registerUserRequest.getUserName().equals("admin@ikiosksng.com")) {
			response.setSuccess(Boolean.TRUE);
			response.setSessionId("sesion-id" + new Random().nextInt());
		} else {
			response.setSuccess(false);
			ErrorOrWarning userAlreadyExists = new ErrorOrWarning();
			userAlreadyExists.setCode("USER_EXISTS");
			userAlreadyExists.setMessage("User with same user name already exists");
			response.getError().add(userAlreadyExists);
		}
		
		return response;
	}

	@PayloadRoot(namespace=ServiceConstants.NAME_SPACE_URI,localPart="update-profile-request")
	public @ResponsePayload UpdateProfileResponse updateProfile(@RequestPayload UpdateProfileRequest updateProfileRequest) {
		UpdateProfileResponse updateProfileResponse = new UpdateProfileResponse();
		updateProfileResponse.setSuccess(true);
		return updateProfileResponse;
	}
	
	
}
