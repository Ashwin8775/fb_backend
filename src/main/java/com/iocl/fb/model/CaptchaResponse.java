package com.iocl.fb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CaptchaResponse {

	private String secret;
	private String captchaImage;
	private String validMagic;
	private String captchaCode;
	
	
	

}
