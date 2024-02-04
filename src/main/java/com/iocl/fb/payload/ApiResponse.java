package com.iocl.fb.payload;

import java.time.ZonedDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;;

@Data
@NoArgsConstructor
public class ApiResponse {

	private Boolean status;
	private String message;
	private ZonedDateTime timestamp;
	private JwtAuthenticationResponse authenticationResponse;
	public ApiResponse(Boolean status, String message) {
		super();
		this.status = status;
		this.message = message;
	}
	public ApiResponse(Boolean status, String message, JwtAuthenticationResponse authenticationResponse) {
		super();
		this.status = status;
		this.message = message;
		this.authenticationResponse = authenticationResponse;
	}
	
	
	
	

}
