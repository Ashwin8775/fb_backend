package com.iocl.fb.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PreferencesReqDto {

	private Long reqId;
	private String status;
	private Integer localityCode;
	private List<HouseListDto> houseList;

}
