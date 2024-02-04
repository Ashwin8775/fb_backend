package com.iocl.fb.scheduling;

import org.springframework.data.jpa.repository.JpaRepository;

public interface JobScheduleRepo extends JpaRepository<JobEntity, Integer> {

}
