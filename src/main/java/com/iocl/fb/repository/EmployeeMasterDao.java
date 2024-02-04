/**
 * 
 */
package com.iocl.fb.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.entities.EmployeeMaster;



/**
 * @author 00507376
 *
 */
public interface EmployeeMasterDao extends CrudRepository<EmployeeMaster, Integer>{

	
	@Query(" from EmployeeMaster where empCode=:employeeId")
	public EmployeeMaster getEmployeeDet(@Param("employeeId") Long employeeId);
	
	
}
