package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewReqDto {

	private String designation;
	private String empCode;
	private String empName;
	private String grade;
	private String iocLocCode;
	private String division;
	private String location;
	private int locationSel;
	private String rdbtn_own_acc;
	private String remarks;
	private int status;
	private String reqId;

}
