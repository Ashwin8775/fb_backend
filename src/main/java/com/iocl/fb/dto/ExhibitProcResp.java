package com.iocl.fb.dto;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ExhibitProcResp {

	@JsonProperty(value = "REQ_ID")
	@Id
	private Long reqId;
	@JsonProperty(value = "EMP_CODE")
	private String empCode;
	@JsonProperty(value = "EMP_NAME")
	private String empName;
	private String email;
	@JsonProperty(value = "MOBILE_NO")
	private String mobileNo;

}
