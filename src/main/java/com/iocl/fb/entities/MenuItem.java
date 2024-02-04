package com.iocl.fb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FBNEW_MENULIST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {

	@Id
	@Column(name = "MENU_ID")
	private int menuId;
	private String MENU_NAME;
	@Column(name = "MLEVEL")
	private int mlevel;
	@Column(name = "MPARENT_ID")
	private int mparentId;
	private String MENU_URL;
	private String UPDATED_BY;
	private String DATE_UPDATED;
	@Column(name = "UPDATE_FLAG")
	private String updateFlag;
	private String DISPLAY_ICON;

}
