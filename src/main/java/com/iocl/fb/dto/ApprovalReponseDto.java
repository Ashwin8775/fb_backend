package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalReponseDto {

	private Long requestId;
	private String empName;
	private String requestDate;
	private int status;
	private String statusDesc;
	private String localityName;
	private int overridingFlag;

	public ApprovalReponseDto(Long requestId, String empName, String requestDate, int status, String statusDesc,
			String localityName) {
		super();
		this.requestId = requestId;
		this.empName = empName;
		this.requestDate = requestDate;
		this.status = status;
		this.statusDesc = statusDesc;
		this.localityName = localityName;
	}

}
