package com.iocl.fb.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.entities.EmailContent;

public interface EmailContentRepo extends JpaRepository<EmailContent, Long> {

	Optional<EmailContent> findByTypeAndUpdateFlag(Long type, String updateFlag);

}
