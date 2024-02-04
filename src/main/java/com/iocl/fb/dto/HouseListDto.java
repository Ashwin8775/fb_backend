package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HouseListDto {

	private Long houseId;
	private String houseNumber;
	private String colName;
	private String residSName;
	private String buildingName;
	private Double areaSqFt;
	private String address1;
	private String address2;
	private Integer pincode;
	private Integer priority;
	private String combinedFields;

	public HouseListDto(Long houseId, String houseNumber, String colName, String residSName, String buildingName,
			Double areaSqFt, String address1, String address2, Integer pincode) {
		super();
		this.houseId = houseId;
		this.houseNumber = houseNumber;
		this.colName = colName;
		this.residSName = residSName;
		this.buildingName = buildingName;
		this.areaSqFt = areaSqFt;
		this.address1 = address1;
		this.address2 = address2;
		this.pincode = pincode;
		this.combinedFields = combineFields(houseNumber, colName, residSName, buildingName, address1, address2,
				String.valueOf(pincode));
	}

	private String combineFields(String... fields) {
		// Combine the fields into a comma-separated string
		return String.join(", ", fields);
	}

}
