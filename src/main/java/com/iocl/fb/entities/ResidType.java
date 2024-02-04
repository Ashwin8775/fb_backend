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
@Table(name = "RESID_TYPE")
public class ResidType {
	
	@Id
	@Column(name = "RESID_TCODE")
	private Long residTcode;
	@Column(name = "RESID_DESC")
	private String residDesc;

}
