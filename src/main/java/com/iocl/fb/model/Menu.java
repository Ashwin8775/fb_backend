package com.iocl.fb.model;

import java.util.List;


public class Menu {

	private int parentMenuId;
	private String parentMenuName;
	private List<Submenu> subMenu;
	private String parentUrl;
	private String displayIcon;
	
	

	public Menu() {
		
	}

	public int getParentMenuId() {
		return parentMenuId;
	}

	public void setParentMenuId(int parentMenuId) {
		this.parentMenuId = parentMenuId;
	}

	

	public List<Submenu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Submenu> subMenu) {
		this.subMenu = subMenu;
	}

	public String getParentMenuName() {
		return parentMenuName;
	}

	public void setParentMenuName(String parentMenuName) {
		this.parentMenuName = parentMenuName;
	}


	public String getParentUrl() {
		return parentUrl;
	}

	public void setParentUrl(String parentUrl) {
		this.parentUrl = parentUrl;
	}

	public String getDisplayIcon() {
		return displayIcon;
	}

	public void setDisplayIcon(String displayIcon) {
		this.displayIcon = displayIcon;
	}

}
