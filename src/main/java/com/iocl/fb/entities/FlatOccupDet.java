package com.iocl.fb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FBNEW_FLAT_OCCUP_DET")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(FlatOccupDetId.class)
public class FlatOccupDet {

	@Id
	@Column(name = "HOUSE_ID")
	private Long houseId;

	@Id
	@Column(name = "REQUEST_ID")
	private Long requestId;

	@Column(name = "APP_CAT")
	private Integer appCat;

	@Column(name = "EMP_CODE")
	private Long empCode;

	@Column(name = "EMP_NAME")
	private String empName;

	@Column(name = "ALLOTMENT_DATE")
	private Date allotmentDate;

	@Column(name = "OCCUPATION_DT")
	private Date occupationDate;

	@Column(name = "HRR_ST_DT")
	private Date hrrStartDate;

	@Column(name = "VACATED_ON")
	private String vacatedOn;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_ON")
	private String updatedOn;

	@Column(name = "HANDOVER_FILE_NAME")
	private String handoverFileName;

	@Lob
	@Column(name = "HANDOVER_FILE")
	private byte[] handoverFile;

	@Column(name = "TAKEOVER_FILE_NAME")
	private String takeoverFileName;

	@Lob
	@Column(name = "TAKEOVER_FILE")
	private byte[] takeoverFile;

	@Column(name = "UNDERTAKING_SUBMITTED")
	private Integer undertakingSubmitted;

	@Column(name = "REMARKS")
	private String remarks;

}
