package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.entities.CategoryRatio;
import com.iocl.fb.entities.CategoryRatioId;

public interface CategoryRatioRepo extends JpaRepository<CategoryRatio, CategoryRatioId> {

	List<CategoryRatio> findByLocalityCodeAndStatus(Integer locality, String status);

}
