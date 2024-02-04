package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllocationInfo {
	private Long requestId;
	
	private Integer category;
	private Integer rank;
	private String prefOrder;

}
