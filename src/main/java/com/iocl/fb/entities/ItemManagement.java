package com.iocl.fb.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FBNEW_ITEM_MGMT")
@IdClass(ItemManagementId.class)
public class ItemManagement {

	@Id
	@Column(name = "LOC_CODE")
	private Integer locCode;

	@Id
	@Column(name = "REQUEST_ID")
	private Long requestId;

	@Id
	@Column(name = "EMP_CODE")
	private Long empCode;

	@Id
	@Column(name = "ITEM_SR_NO")
	private Long itemSrNo;
	
	@Id
	@Column(name = "QTY")
	private Long qty;


	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_ON")
	private Timestamp updatedOn;

}
