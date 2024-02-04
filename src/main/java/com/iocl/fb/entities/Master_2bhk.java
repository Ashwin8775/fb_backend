package com.iocl.fb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FBNEW_2BHK_MASTER")
public class Master_2bhk {

	@Id
	@Column(name = "SNO")
	private Long sno;

	@Column(name = "LOC_CODE")
	private Long locCode;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "ITEM")
	private String item;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "UNIT")
	private String unit;

	@Column(name = "DIVISION")
	private String division;
	
	

}
