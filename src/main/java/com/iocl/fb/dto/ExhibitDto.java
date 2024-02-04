package com.iocl.fb.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExhibitDto {

	private Long empCode;
	private Integer locCode;
	private List<LocalityDto> localities;

}
