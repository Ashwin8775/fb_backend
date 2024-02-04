package com.iocl.fb.entities;

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
@Table(name = "FBNEW_CAT_RATIO")
@IdClass(CategoryRatioId.class)
public class CategoryRatio {

	@Id
	@Column(name = "LOC_CODE")
	private int locCode;

	@Id
	@Column(name = "LOCALITY_CODE")
	private int localityCode;

	@Id
	@Column(name = "CAT_CODE")
	private int catCode;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "RATIO")
	private int ratio;

}
