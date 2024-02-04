package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VacantFlatdetailsDto {
	
	private Long houseId;
	private String houseNumber;
	private String localityName;
	private String address1;
	private String residSName;

}
