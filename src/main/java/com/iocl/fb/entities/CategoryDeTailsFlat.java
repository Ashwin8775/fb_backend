package com.iocl.fb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "FBNEW_CATEGORY_LIST")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CategoryDeTailsFlat {
	
	@Id
	@Column(name = "CATEGORY_ID")
	private Long categoryId;
	@Column(name = "CATEGORY_NAME")
	private String categoryName;
	
	@Column(name = "DIVISION")
	private String division;

}
