package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.entities.MenuItem;

public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

	@Query("from MenuItem where mlevel=1 and menuId IN " + "(select menuId from RefRoleAuth  where roleId=:roleId)"
			+ "and updateFlag<>'D' order by menuId")
	public List<MenuItem> findParentMenu(@Param("roleId") String roleId);

	@Query("from MenuItem where mlevel=2 and mparentId=:parentId and menuId IN "
			+ "(select menuId from RefRoleAuth  where roleId=:roleId and updateFlag<>'D')"
			+ "and updateFlag<>'D' order by menuId")
	public List<MenuItem> findSubmenu(@Param("parentId") Integer parentId, @Param("roleId") String roleId);
//
//	
//	
//	public List<MenuItem> findByMlevelAndUpdateFlagOrderByMenuId(int level,String updateflag);
//	
//	public List<MenuItem> findByMlevelAndUpdateFlagAndMparentIdOrderByMenuId(int level,String updateflag,int parId);

}
