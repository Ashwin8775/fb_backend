package com.iocl.fb.views;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "DYNAMIC_WAITLIST_VIEW")
public class DynamicWaitListView {

	@Id
	@Column(name = "REQUEST_ID")
	private Long requestId;

	@Column(name = "LOC_CODE")
	private Integer locCode;

	@Column(name = "LOCALITY_CODE")
	private Integer localityCode;

	@Column(name = "APP_CAT")
	private Integer appCat;

	@Column(name = "RNK")
	private Integer rnk;

	@Column(name = "LOCALITY_NAME")
	private String localityName;

	@Column(name = "EMP_CODE")
	private Long empCode;

	@Column(name = "EMP_NAME")
	private String empName;

	@Column(name = "REQUEST_DATE")
	private String requestDate;

	@Column(name = "CURR_DESIG")
	private String currDesig;

	@Column(name = "CURR_GRADE")
	private Integer currGrade;

	@Column(name = "SENIORITY_NO")
	private Long seniorityNo;

	@Column(name = "STATUS")
	private Integer status;
	
	@Column(name = "EMAIL_ID")
	private String emailId;
	
	@Column(name = "MOBILE_NUMBER")
	private String mobileNo;

}
