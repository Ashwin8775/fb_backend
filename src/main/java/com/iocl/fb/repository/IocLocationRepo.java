package com.iocl.fb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.entities.IocLocation;

public interface IocLocationRepo extends JpaRepository<IocLocation, Integer> {

	@Query("SELECT loc.locCode FROM IocLocation loc WHERE loc.iocLocCode = :iocLocCode")
	Integer findLocCodeByIocLocCode(@Param("iocLocCode") int iocLocCode);

}
