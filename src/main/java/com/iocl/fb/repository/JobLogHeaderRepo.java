package com.iocl.fb.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.iocl.fb.entities.JobLogHead;

public interface JobLogHeaderRepo extends JpaRepository<JobLogHead, Long> {
	@Modifying
	@Query("UPDATE JobLogHead e SET e.status = :newStatus WHERE e.logId = :logId")
	void updateStatus(String newStatus, Long logId);

}
