package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iocl.fb.dto.GlobalVariableDto;
import com.iocl.fb.entities.GlobalVariables;

public interface GlobalVariableRepo extends JpaRepository<GlobalVariables, Long> {

	@Query("SELECT NEW com.iocl.fb.dto.GlobalVariableDto(gv.catValue, gv.catDescription) " + "FROM GlobalVariables gv "
			+ "WHERE gv.category = :category AND gv.catValue NOT IN (:excludedValues)")
	List<GlobalVariableDto> findByCategoryAndCatValueNotIn(String category, List<Integer> excludedValues);

}
