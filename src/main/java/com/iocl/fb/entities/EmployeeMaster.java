/**
 * 
 */
package com.iocl.fb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 00507376
 *
 */

@Entity
@Table(name = "COM_EMP_VIEW")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeMaster {

	@Id
	@Column(name = "EMP_CODE")
	private Long empCode;

	@Column(name = "SAP_USER_ID")
	private String sapUserId;

	@Column(name = "EMP_INI_CODE")
	private String empIniCode;

	@Column(name = "EMP_INI")
	private String empIni;

	@Column(name = "EMP_NAME")
	private String empName;

	@Column(name = "EMP_FNAME")
	private String empFname;

	@Column(name = "EMP_LNAME")
	private String empLname;

	@Column(name = "MOBILE_NO")
	private String mobileNumber;

	@Column(name = "EMAIL_ID")
	private String emailId;

	@Column(name = "LOC_CODE")
	private String locCode;

	@Column(name = "LOC_NAME")
	private String locName;

	@Column(name = "DESIGNATION")
	private String designation;

	@Column(name = "CURR_COMP_CODE")
	private String currCompCode;

	@Column(name = "CURR_COMP")
	private String currComp;
	
	@Column(name = "PSA")
	private String psa;
	
	@Column(name = "PSA_CODE")
	private String psaCode;
	
	@Column(name = "CURRENT_DIVN_CD")
	private String divisionCode;
	
	@Column(name = "CURRENT_DIVN")
	private String divisionName;
	
	@Column(name = "EMP_SUB_GRP_CODE")
	private String grade;
	
	

}
