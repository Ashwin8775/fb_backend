package com.iocl.fb.model;

public class Submenu {

	private String menuName;
	private String menuUrl;

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public Submenu(String menuName, String menuUrl) {
		super();
		this.menuName = menuName;
		this.menuUrl = menuUrl;
	}

	public Submenu() {

	}

}
