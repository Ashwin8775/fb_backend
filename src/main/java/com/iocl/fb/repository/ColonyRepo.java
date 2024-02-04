package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.iocl.fb.entities.Colony;

public interface ColonyRepo extends JpaRepository<Colony, Long> {
	
	@Query("SELECT c.colCode AS colCode, c.colName AS colName FROM Colony c where c.localityCode = :localityCode")
    List<ColonyProjection> findAllColCodeAndColName(@Param("localityCode") Integer localityCode);

    interface ColonyProjection {
        long getColCode();
        String getColName();
    }

}
