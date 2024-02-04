package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.dto.LocalityDto;
import com.iocl.fb.entities.Locality;

public interface LocalityRepo extends JpaRepository<Locality, Integer> {

	@Query("SELECT new com.iocl.fb.dto.LocalityDto(e.localityCode, e.localityName) FROM Locality e WHERE e.locCode = :loccode  and e.residScode IN :residScodeList")
	List<LocalityDto> findLocalityCodeAndlLocalityNameByLocCode(@Param("loccode") int loccode,
			@Param("residScodeList") List<Long> residScodeList);

	@Query("SELECT new com.iocl.fb.dto.LocalityDto(e.localityCode, e.localityName) FROM Locality e WHERE e.locCode = :loccode")
	List<LocalityDto> findByLocCode(@Param("loccode") int loccode);

//	@Modifying
//	@Query("UPDATE Locality SET lastAllottedCat= :category,lastAllotedCount= :count where localityCode= :localityCode")
//	int updateCategoryCount(int category, int count, int localityCode);

	@Modifying
	@Query("UPDATE Locality SET lastAllottedCat = :category, lastAllotedCount = CASE WHEN lastAllottedCat = :category THEN (lastAllotedCount + 1) ELSE 1 END WHERE localityCode = :localityCode")
	int updateCategoryCount(int category,int localityCode);

}
