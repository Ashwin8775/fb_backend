package com.iocl.fb.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalReq {

	private long empCode;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date fromDate;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
	private Date toDate;

	private long requestId;
	private int localityCode;

	private String remarks;

	@JsonProperty("fromDate")
	public String getFormattedFromDate() {
		// Convert Date to 'DD-MMM-YYYY' format
		return new java.text.SimpleDateFormat("dd-MMM-YYYY").format(fromDate);
	}

	@JsonProperty("toDate")
	public String getFormattedToDate() {
		// Convert Date to 'DD-MMM-YYYY' format
		return new java.text.SimpleDateFormat("dd-MMM-YYYY").format(toDate);
	}

}
