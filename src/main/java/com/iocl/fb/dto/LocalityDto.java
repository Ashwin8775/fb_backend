package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocalityDto {

	private int localityCode;
	private String localityName;
	private Long vacant;

	public LocalityDto(int localityCode, String localityName) {
		super();
		this.localityCode = localityCode;
		this.localityName = localityName;
	}

}
