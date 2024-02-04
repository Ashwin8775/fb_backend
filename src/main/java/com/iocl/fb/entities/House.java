package com.iocl.fb.entities;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FBNEW_FB_HOUSE")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Cacheable(false)
public class House {

	@Id
	@Column(name = "HOUSE_ID")
	private Long houseId;

	@Column(name = "ROOM_NO")
	private Integer roomNumber;

	@Column(name = "HOUSE_NO")
	private String houseNumber;

	@Column(name = "RESID_TCODE")
	private Long residTCode;

	@Column(name = "RESID_SCODE")
	private Long residSCode;

	@Column(name = "FLOOR_NO")
	private Integer floorNumber;

	@Column(name = "BUILDING_NM")
	private String buildingName;

	@Column(name = "COL_CODE")
	private Long colCode;

	@Column(name = "STATUS")
	private Integer status;

	@Column(name = "AREA_SQFT")
	private Double areaSqft;

	@Column(name = "TOTAL_OCCUPANTS")
	private Integer totalOccupants;

	@Column(name = "CURRENT_OCCUPANTS")
	private Integer currentOccupants;

	@Column(name = "MIN_GRADE_CODE")
	private Integer minGradeCode;

	@Column(name = "INTIMATION_SENT")
	private Integer intimationSent;

	@Column(name = "RENOVATED_FLAG")
	private Integer renovatedFlag;

	@Column(name = "VACANT_REMARKS")
	private String vacantRemarks;

}
