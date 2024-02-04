package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetailsDto {

	private Long requestId;
	private int appCat;
	private int locCode;
	private int localityCode;
	private int itemStatus;
	private String prefOrder;
	private int rank;

}
