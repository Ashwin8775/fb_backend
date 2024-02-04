package com.iocl.fb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.entities.SmsContent;

public interface SmsContentRepo extends JpaRepository<SmsContent, String>{

}
