package com.iocl.fb.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AllotmentDetailsId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long requestId;

	private Long runId;

}
