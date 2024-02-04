package com.iocl.fb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.entities.AllotmentDetails;
import com.iocl.fb.entities.AllotmentDetailsId;

public interface AllotmentDetailsRepo extends JpaRepository<AllotmentDetails, AllotmentDetailsId> {

}
