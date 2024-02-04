package com.iocl.fb.entities;

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
@Table(name = "FBNEW_GLOBAL_VARIABLES")
public class GlobalVariables {
	
	@Id
    @Column(name = "CATEGORY_ID")
    private Long categoryId;

    @Column(name = "CATEGORY")
    private String category;

    @Column(name = "CAT_NAME")
    private String catName;

    @Column(name = "CAT_VALUE")
    private int catValue;

    @Column(name = "CAT_DESCRIPTION")
    private String catDescription;

}
