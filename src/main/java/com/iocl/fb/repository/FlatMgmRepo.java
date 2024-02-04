package com.iocl.fb.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.entities.FlatMgm;
import com.iocl.fb.entities.FlatMgmId;

public interface FlatMgmRepo extends JpaRepository<FlatMgm, FlatMgmId> {

}
