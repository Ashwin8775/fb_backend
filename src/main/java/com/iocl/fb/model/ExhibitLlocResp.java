package com.iocl.fb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitLlocResp {

	private Long reqId;
	private String empCode;
	private String empName;
	private String emailId;
	private String mobileNumber;
	private String locality;
	private String mailStatus;
	private String mailMsg;
	private String smsStatus;
	private String smsMsg;

}
