package com.iocl.fb.entities;

import java.util.Date;

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
@Table(name = "FBNEW_FLAT_MGMT")
@Entity
@IdClass(FlatMgmId.class)
public class FlatMgm {

	@Id
	@Column(name = "HOUSE_ID")
	private Long houseId;

	@Column(name = "CURR_STATUS")
	private Integer currStatus;

	@Column(name = "PREV_STATUS")
	private Integer prevStatus;

	@Column(name = "UPDATED_BY")
	private Long updatedBy;

	@Id
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	@Column(name = "REMARKS")
	private String remarks;

	@Column(name = "PREV_RESID_SIZE")
	private Long prevResidSize;

	@Column(name = "CURR_RESID_SIZE")
	private Long currResidSize;

}
