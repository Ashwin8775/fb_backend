package com.iocl.fb.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iocl.fb.entities.MenuItem;
import com.iocl.fb.model.Menu;
import com.iocl.fb.model.Submenu;
import com.iocl.fb.repository.MenuItemRepository;

@Service
public class MenuService {

	@Autowired
	MenuItemRepository menRepo;

	public List<Menu> findAllMenu(String roleId) {
		List<MenuItem> menuList = menRepo.findParentMenu(roleId);
		List<Menu> menuArray = new ArrayList<>();

		for (int i = 0; i < menuList.size(); i++) {

			Menu menu = new Menu();
			menu.setParentMenuId(menuList.get(i).getMenuId());
			menu.setParentMenuName(menuList.get(i).getMENU_NAME());
			List<MenuItem> submenuListDb = menRepo.findSubmenu(menuList.get(i).getMparentId(), roleId);
			ArrayList<Submenu> submenus = new ArrayList<>();
			for (MenuItem tems : submenuListDb) {
				submenus.add(new Submenu(tems.getMENU_NAME(), tems.getMENU_URL()));
			}
			menu.setSubMenu(submenus);
			menu.setParentUrl(menuList.get(i).getMENU_URL());
			menu.setDisplayIcon(menuList.get(i).getDISPLAY_ICON());
			menuArray.add(menu);

		}

		return menuArray;
	}

}
