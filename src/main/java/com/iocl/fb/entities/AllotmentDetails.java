package com.iocl.fb.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FBNEW_ALLOTMENT_DET")
@IdClass(AllotmentDetailsId.class)
public class AllotmentDetails {

	@Id
	@Column(name = "REQUEST_ID")
	private Long requestId;

	@Id
	@Column(name = "RUN_ID")
	private Long runId;

	@Column(name = "EMP_CODE")
	private Long empCode;

	@Column(name = "EMP_NAME")
	private String empName;

	@Column(name = "GRADE")
	private Integer grade;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "APP_CAT")
	private Integer appCat;

	@Column(name = "HOUSE_ID")
	private Long houseId;

	@Column(name = "LOCALITY_CODE")
	private Integer localityCode;

	@Column(name = "WL_ON_ALLOTMENT")
	private Long wlOnAllotment;

	@Column(name = "OVERRIDING_FLAG")
	private Integer overridingFlag;

	@Column(name = "ALLOTMENT_DATE")
	private LocalDate allotmentDate;

	@Column(name = "ACCEPT_FLAG")
	private Integer acceptFlag;

	@Column(name = "DATE_ACCEPTED")
	private LocalDate dateAccepted;

	@Column(name = "CANCEL_FLAG")
	private Integer cancelFlag;

	@Column(name = "CANCEL_DATE")
	private LocalDate cancelDate;

	@Column(name = "REJECT_REMARK")
	private String rejectRemark;

	@Column(name = "CANCEL_REMARK")
	private String cancelRemark;

}
