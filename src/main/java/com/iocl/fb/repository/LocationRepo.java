package com.iocl.fb.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.iocl.fb.dto.LocationDTO;
import com.iocl.fb.entities.Location;

public interface LocationRepo extends JpaRepository<Location, Integer> {
	

    @Query("SELECT l.locCode AS locCode, l.locName AS locName FROM Location l")
    List<LocationProjection> findAllLocCodeAndLocName();
    
    @Query("SELECT new com.iocl.fb.dto.LocationDTO(a.locCode, a.locName) " +
            "FROM Location a " +
            "INNER JOIN Admin b ON a.locCode = b.locCode " +
            "WHERE b.empCode = :empCode AND b.role IN ('M', 'A')")
     List<LocationDTO> findLocationsByAdmin(Long empCode);

    interface LocationProjection {
        int getLocCode();
        String getLocName();
    }

}
