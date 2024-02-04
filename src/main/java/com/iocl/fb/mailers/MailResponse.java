package com.iocl.fb.mailers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailResponse {

	private boolean status;
	private MailApiResponse apiResp;
	private String message;

}
