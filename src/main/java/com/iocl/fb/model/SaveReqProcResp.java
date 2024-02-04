package com.iocl.fb.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SaveReqProcResp {
	
	@JsonProperty(value = "REQ_ID")
	@Id 
	private String reqId;
	@JsonProperty(value = "STATUS")
	private String status;

}
