package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.entities.Sms;

public interface SmsRepository extends JpaRepository<Sms, Long> {

	@Modifying
	@Query("UPDATE Sms SET updateFlag= :updateFlag where smsUid IN :smsUidList")
	int updateStatus(@Param("updateFlag") String updateFlag, @Param("smsUidList") List<Long> smsUidList);

}
