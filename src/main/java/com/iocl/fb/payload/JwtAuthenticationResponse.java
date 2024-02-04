package com.iocl.fb.payload;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtAuthenticationResponse {

	private String accessToken;
	private String tokenType = "Bearer";

	private String userId; // encrypted user ID for authentication header (empCode, vendorCode+srCode)
	// emp info
	private String empCode;
	private String empName;
	private String designation;
	private String locCode;
	private String locName;
	private String mobileNo;
	private String emailId;
	private String vendorCode;
	private String srCode;
	private String vendorName;

	private String roleCatId;
	private String roleCatDesc;
	private String userFlag;
	private int userLevel; // for IOC Employees
	private int pwdChg; // for SR

	private String compCode;
	private String compCodeDesc;
	private String regionCode;
	
	private String psa;
	
	private String psaCode;
	
	private String divisionCode;
	
	private String divisionName;
	
	private String grade;

	private boolean isProduction;
	private long sessionId;
	private String welcomeMessage;
	private Integer locId;

	private Date formSubmitDate;
	
	

	public JwtAuthenticationResponse(String accessToken) {
		super();
		this.accessToken = accessToken;
	}
}
