package com.iocl.fb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.fb.model.Menu;
import com.iocl.fb.service.MenuService;

/**
 * @author t_Salian
 *
 */
@RestController
@RequestMapping("/Homepage")
@CrossOrigin(origins = "*")
public class HomePageController {

	@Autowired
	MenuService menSer;

	@GetMapping("/menuList")
	public List<Menu> menuListDetails(@RequestParam String roleId) {

		List<Menu> findAllMenu = menSer.findAllMenu(roleId);
		return findAllMenu;

	}

}
