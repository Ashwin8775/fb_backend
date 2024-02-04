package com.iocl.fb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.entities.Email;

public interface Emailrepo extends JpaRepository<Email, Long> {

	@Modifying
	@Query("UPDATE Email SET updateFlag= :updateFlag where emailUid = :emailUid")
	int updateStatus(@Param("updateFlag") String updateFlag, @Param("emailUid") Long emailUid);

}
