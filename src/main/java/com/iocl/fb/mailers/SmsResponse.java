package com.iocl.fb.mailers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsResponse {

	private Boolean status;
	private SmsApiResponse apiresponse;
	private String message;

}
