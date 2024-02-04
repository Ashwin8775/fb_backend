package com.iocl.fb.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author t_Salian
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailContentId implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long type;
	private String updateFlag;

}
