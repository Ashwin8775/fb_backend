package com.iocl.fb.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.iocl.fb.entities.RunHeader;

public interface RunHeaderRepo extends JpaRepository<RunHeader, Long> {

	Optional<RunHeader> findByLocCodeAndLocalityCodeAndEndDate(int locCode, int localityCode, LocalDate endDate);

}
