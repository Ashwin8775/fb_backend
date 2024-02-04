package com.iocl.fb.scheduling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iocl.fb.jobs.ExhibitServiceJob;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SchedularCalls {

	@Autowired
	ExhibitServiceJob service;

	public void allotmentJob() {

		log.info("Allotment Done Successfully");

	}

	public void exhibitJob() {
		log.info("Exhibit JOB Started");
		service.exhibitJobSchedule();

		log.info("Exhibit JOB Completed");

	}

}
