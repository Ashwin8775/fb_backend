package com.iocl.fb.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryRatioId implements Serializable {
	/**
	* 
	*/
	private static final long serialVersionUID = 1L;

	private int locCode;

	private int localityCode;

	private int catCode;

}
