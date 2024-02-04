package com.iocl.fb.entities;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlatMgmId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long houseId;

	private Date updatedOn;

}
