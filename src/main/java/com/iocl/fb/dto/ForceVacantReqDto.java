package com.iocl.fb.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ForceVacantReqDto {

	private Date vacDate;
	private String remarks;
	private long reqId;
	private long houseId;
	private int localityCode;
	private String empCode;

}
