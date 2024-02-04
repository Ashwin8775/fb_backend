package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.entities.Master_2bhk;

public interface Master2BhkRepo extends JpaRepository<Master_2bhk, Long> {

	List<Master_2bhk> findByLocCodeOrderByDivisionAscSnoAsc(Long locCode);

}
