package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.views.DynamicWaitListView;

public interface DynamicWaitListRepo extends JpaRepository<DynamicWaitListView, Long> {

	List<DynamicWaitListView> findByLocalityCodeAndAppCatAndRnkGreaterThanOrderByRnkAsc(Integer locality,
			Integer appCat, Integer rnk, Pageable pageable);

	List<DynamicWaitListView> findByLocalityCodeAndAppCatOrderByRnkAsc(Integer locality, Integer appCat);
	
	List<DynamicWaitListView> findAllByRequestIdIn(List<Long> ids);
}
