package com.iocl.fb.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateTakeoverPdfDto {

	private String occempcode;
	private String occempName;
	private Integer occLocId;
	private String flatNo;
	private Long requestId;
	private String houseNo;
	
	
	private String reading;
	private String elecMetNo;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date date;

	private String empCode;
	private String empName;
	private String designation;
	private List<CategoryBean>  itemDets;
	
	@JsonProperty("date")
	public String getFormattedOnDate() {
		// Convert Date to 'DD-MMM-YYYY' format
		return new java.text.SimpleDateFormat("dd-MMM-YYYY").format(date);
	}

}
