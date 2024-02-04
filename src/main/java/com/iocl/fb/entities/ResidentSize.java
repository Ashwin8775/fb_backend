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
@Table(name = "RESID_SIZE")
public class ResidentSize {

	@Id
	@Column(name = "RESID_SCODE")
	private Long residSCode;

	@Column(name = "RESID_SNAME")
	private String residSName;

}
