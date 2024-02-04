package com.iocl.fb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FBNEW_REQ_REJECTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestRejection {

	@Id
	@Column(name = "REQUEST_ID")
	private Long requestId;

	@Column(name = "LOCALITY_CODE")
	private Integer localityCode;

	@Column(name = "REJECTED_BY")
	private Long rejectedBy;

	@Column(name = "REJECTED_ON")
	private Date rejectedOn;

	@Column(name = "REASON")
	private String reason;

	@Column(name = "PREV_STATUS")
	private Integer prevStatus;

	public RequestRejection(Long requestId, Integer localityCode, Long rejectedBy, String reason, Integer prevStatus) {
		super();
		this.requestId = requestId;
		this.localityCode = localityCode;
		this.rejectedBy = rejectedBy;
		this.reason = reason;
		this.prevStatus = prevStatus;
		this.rejectedOn = new Date();
	}

}
