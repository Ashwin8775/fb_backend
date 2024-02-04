package com.iocl.fb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FBNEW_REQUEST_HDR")
public class FbRequestHeader {

	@Id
	@Column(name = "REQUEST_ID")
	@GeneratedValue(generator = "idSequence")
	@SequenceGenerator(schema = "FLATBKG", name = "idSequence", sequenceName = "SEQ_FLATBOOKING", allocationSize = 1)
	private Long requestId;

	@Column(name = "REQUEST_DATE")
	private Date requestDate;

	@Column(name = "EMP_CODE")
	private Long empCode;

	@Column(name = "EMP_NAME")
	private String empName;

	@Column(name = "IOC_LOC_CODE")
	private int iocLocCode;

	@Column(name = "GRADE")
	private int grade;

	@Column(name = "DESIG")
	private String desig;

	@Column(name = "DIVISION")
	private String division;

	@Column(name = "CURR_IOC_LOC_CODE")
	private int currIocLocCode;

	@Column(name = "CURR_GRADE")
	private int currGrade;

	@Column(name = "CURR_DESIG")
	private String currDesig;

	@Column(name = "SENIORITY_NO")
	private int seniorityNo;

	@Column(name = "APP_CAT")
	private int appCat;

	@Column(name = "STATUS")
	private int status;

	@Column(name = "JOINING_REP_NO")
	private String joiningRepNo;

	@Column(name = "JOINING_REP_DATE")
	private Date joiningRepDate;

	@Column(name = "JOINING_REP_NAME")
	private String joiningRepName;

	@Lob
	@Column(name = "JOINING_REPORT")
	private byte[] joiningReport;

	@Column(name = "FAMILY_RETN")
	private int familyRetn;

	@Column(name = "FAMILY_RET_NAME")
	private String familyRetName;

	@Lob
	@Column(name = "FAMILY_RET_FILE")
	private byte[] familyRetFile;

	@Column(name = "OWN_ACCO")
	private int ownAcco;

	@Column(name = "CIRCULAR_TYPE")
	private Long circularType;

	@Column(name = "DATE_APPROVED")
	private Date dateApproved;

	@Column(name = "ADMIN_REMARKS")
	private String adminRemarks;

	@Column(name = "ADMIN")
	private Long admin;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "REJECTION_COUNT")
	private int rejectionCount;

	@Column(name = "OVERRIDING_FLAG")
	private int overridingFlag;

	@Column(name = "OVERRIDING_REASON")
	private String overridingReason;

	@Column(name = "OVERRIDDEN_BY")
	private Long overriddenBy;

	@Lob
	@Column(name = "OVERRIDING_APPROVAL")
	private byte[] overridingApproval;

	@Column(name = "APPROVAL_FILE_NAME")
	private String approvalFileName;

	@Column(name = "CONTACT1")
	private Long contact1;

	@Column(name = "CONTACT2")
	private Long contact2;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "ALTERNATE_EMAIL")
	private String alternateEmail;

}
