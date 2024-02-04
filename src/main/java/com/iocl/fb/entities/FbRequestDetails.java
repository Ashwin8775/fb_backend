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
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FBNEW_REQUEST_DETL")
public class FbRequestDetails {

	@Id
	@Column(name = "REQUEST_ID")
	private long requestId;

	@Column(name = "APP_CAT")
	private int appCat;

	@Column(name = "GEN_WL")
	private Integer genWl;

	@Column(name = "LOCN_WL")
	private Integer locnWl;

	@Column(name = "LOC_CODE")
	private int locCode;

	@Column(name = "LOCALITY_CODE")
	private int localityCode;

	@Column(name = "CLUSTER_ID")
	private int clusterId;

	@Column(name = "COL_CODE")
	private Long colCode;

	@Column(name = "ITEM_STATUS")
	private int itemStatus;

	@Column(name = "PREF_ORDER")
	private String prefOrder;

	@Column(name = "APPLICATION_DATE")
	private Date applicationDate;

	@Column(name = "REJECTION_COUNT")
	private int rejectionCount;

	@Column(name = "REJECTION_DATE")
	private Date rejectionDate;

	@Column(name = "DATE_CREATED")
	private Date dateCreated;

	@Column(name = "DATE_UPDATED")
	private Date dateUpdated;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Column(name = "REMARKS")
	private String remarks;

}
