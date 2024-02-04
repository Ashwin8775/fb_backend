package com.iocl.fb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseDetsReqDto {

	private Long houseId;
	private String houseNo;
	private Integer status;
	private Long residSCode;
	private Long empCode;
	private String remarks;
	

}
