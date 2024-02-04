package com.iocl.fb.entities;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.iocl.fb.model.RoleEmbed;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FBNEW_ROLE_AUTHORITY")
@IdClass(RoleEmbed.class)
public class RefRoleAuth {

	@Id
	@Column(name = "ROLE_ID")
	private String roleId;

	@Id
	@Column(name = "MENU_ID")
	private int menuId;

	@Column(name = "UPDATE_FLAG")
	private String updateFlag;
	@Column(name = "UPDATED_BY")
	private String updatedBy;
	@Column(name = "DATE_UPDATED")
	private Date dateUpdated;

}
