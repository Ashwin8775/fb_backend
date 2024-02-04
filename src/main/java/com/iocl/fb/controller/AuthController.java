package com.iocl.fb.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.fb.config.JwtTokenProvider;
import com.iocl.fb.dto.AllocationInfo;
import com.iocl.fb.dto.LocalityDto;
import com.iocl.fb.entities.RunHeader;
import com.iocl.fb.jobs.AllotmentServiceJob;
import com.iocl.fb.jobs.ExhibitServiceJob;
import com.iocl.fb.login.AccessCheckService;
import com.iocl.fb.login.RSAService;
import com.iocl.fb.login.SetLogInTimeConstantsService;
import com.iocl.fb.model.CaptchaResponse;
import com.iocl.fb.payload.ApiResponse;
import com.iocl.fb.payload.JwtAuthenticationResponse;
import com.iocl.fb.payload.LoginRequest;
import com.iocl.fb.repository.DynamicWaitListRepo;
import com.iocl.fb.repository.FbHouseRepo;
import com.iocl.fb.repository.RunHeaderRepo;
import com.iocl.fb.service.FbLoginService;
import com.iocl.fb.util.MessageConstants;
import com.iocl.fb.views.DynamicWaitListView;

/**
 * @author t_Salian
 *
 */
@RestController
@CrossOrigin(origins = "*")

//@CrossOrigin(origins = { "https://associates.indianoil.co.in","https://spandan.indianoil.co.in","http://10.146.65.56"})
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private FbLoginService loginService;

	@Autowired
	private RSAService rsaService;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	SetLogInTimeConstantsService setLogInTimeConstantsService;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	private AccessCheckService accessCheckService;

	// Remove Below Code
	@Autowired
	AllotmentServiceJob job;

	@GetMapping(value = "/generate-captcha")
	public ResponseEntity<CaptchaResponse> generateCAPTCHA() {
		return ResponseEntity.ok(loginService.generateCaptchaResponse());
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@PostMapping("/generate-token")
	public ResponseEntity<JwtAuthenticationResponse> generateToken(@RequestBody LoginRequest loginRequest) {

		if (loginRequest.getUserName().isEmpty() || loginRequest.getPassword().isEmpty()) {
			return new ResponseEntity(new ApiResponse(false, MessageConstants.USERNAME_OR_PASSWORD_EMPTY),
					HttpStatus.BAD_REQUEST);
		}
		Authentication authentication = null;
		String encryptedPassword = null;
		String decodedPassword = null;
		String specialUser = null;

		try {
			encryptedPassword = loginRequest.getPassword();
			decodedPassword = rsaService.decrypt(Base64.getDecoder().decode(encryptedPassword.getBytes()));

			// Test Code
			// Ashwin

			if (specialUser == null)
				authentication = authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), decodedPassword));
		} catch (Exception e) {
			System.out.println("Exception mesage is  " + e.getMessage());
			System.err.println("Wrong login credentials");
		}

		if ((authentication != null && authentication.isAuthenticated()) || specialUser != null) {

			String jwt = tokenProvider.generateToken(loginRequest.getUserName());
			JwtAuthenticationResponse authenticationResponse = new JwtAuthenticationResponse(jwt);

			if (!accessCheckService.isAccessAllowed(loginRequest.getUserName())) {
				return new ResponseEntity(new ApiResponse(false, MessageConstants.INVALID_ACCESS),
						HttpStatus.UNAUTHORIZED);
			}

			authenticationResponse = setLogInTimeConstantsService.storeLoginTimeConstants(authenticationResponse,
					loginRequest.getUserName(), specialUser);

			// return ResponseEntity.ok(authenticationResponse);
			return new ResponseEntity(new ApiResponse(true, MessageConstants.VALID_TOKEN, authenticationResponse),
					HttpStatus.OK);
		}

		return new ResponseEntity(new ApiResponse(false, MessageConstants.USERNAME_OR_PASSWORD_INVALID), HttpStatus.OK);
	}

	@GetMapping(value = "/test/{locality}/{vacant}")
	public void test(@PathVariable("locality") Integer locality, @PathVariable("vacant") Integer vacant) {

		job.allotmentJobSchedule();

	}

}
