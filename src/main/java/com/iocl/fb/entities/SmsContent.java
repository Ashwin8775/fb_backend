package com.iocl.fb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FBNEW_SMS_CONTENT")
public class SmsContent {

	@Id
	@Column(name = "REF_ID")
	private Integer refId;

	@Column(name = "PARAM_NAME")
	private String paramName;

	@Column(name = "DEFAULT_VALUE")
	private String defaultValue;

	@Column(name = "TYPE")
	private String type;

}
