package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryBean {
	
	
	private Integer sequence;
	private Long srno;
	private String category;
	private Long qty;
	private Long residScode;
	private Long type;
	private String unit;
	
	public CategoryBean(Long srno, String category, String unit) {
		super();
		this.srno = srno;
		this.category = category;
		this.unit = unit;
	}
	
	

}
