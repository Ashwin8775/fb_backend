package com.iocl.fb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.entities.RequestRejection;

public interface FbReqRejectionRepo extends JpaRepository<RequestRejection, Long> {

}
