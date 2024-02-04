package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlatMgmReq {
	private Integer location;
	private Integer locality;
	private Long colony;
	private Integer status;
	private Long empCode;

}
