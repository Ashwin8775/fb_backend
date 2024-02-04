package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.iocl.fb.entities.ItemDetails;

public interface ItemDetailsRepo extends JpaRepository<ItemDetails, Long> {

	List<ItemDetails> findByLocCodeAndResidScodeAndStatusOrderByTypeAscSnoAsc(Long locCode, Long residSCode, String status);

	@Modifying
	@Query("UPDATE ItemDetails SET status= :status where sno = :sno")
	int updateBySno(String status, Long sno);

	@Query(value = "SELECT MAX(sno) FROM ItemDetails")
	Long findMaxSno();

}
