package com.iocl.fb.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FBNEW_ITEM_DETAILS")
public class ItemDetails {

	@Id
	@Column(name = "SNO")
	private Long sno;

	@Column(name = "LOC_CODE")
	private Long locCode;

	@Column(name = "TYPE")
	private Long type;

	@Column(name = "ITEM")
	private String item;

	@Column(name = "STATUS")
	private String status = "A";

	@Column(name = "UNIT")
	private String unit;
	
	@Column(name = "QTY")
	private Long qty;

	
	@Column(name = "RESID_SCODE")
	private Long residScode;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "TYPE", referencedColumnName = "CATEGORY_ID", insertable = false, updatable = false)
	private CategoryDeTailsFlat category;
	
	

}
