package com.iocl.fb.model;

import java.io.Serializable;



public class RoleEmbed  implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String  roleId;
	
	private int menuId;
	public String getRoleId() {
		return roleId;
	}
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	public int getMenuId() {
		return menuId;
	}
	public void setMenuId(int menuId) {
		this.menuId = menuId;
	}
	public RoleEmbed(String roleId, int menuId) {
		super();
		this.roleId = roleId;
		this.menuId = menuId;
	}
	public RoleEmbed() {
		
	}
	@Override
	public String toString() {
		return "RoleEmbed [roleId=" + roleId + ", menuId=" + menuId + "]";
	}
	
	

}
