package com.iocl.fb.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iocl.fb.entities.JobLogDetail;
import com.iocl.fb.entities.JobLogHead;
import com.iocl.fb.repository.JobLogDetailRepo;
import com.iocl.fb.repository.JobLogHeaderRepo;

@Service
public class JobLogService {

	@Autowired
	JobLogHeaderRepo headerRepo;

	@Autowired
	JobLogDetailRepo detailRepo;

	public Long saveJobHeader(Long jobId, String status, String errorMsg) {
		JobLogHead head = new JobLogHead();
		head.setJobId(jobId);
		head.setStatus(status);
		head.setErrorMessage(errorMsg);

		LocalDate currentDate = LocalDate.now();

		// Define the desired date format
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy");

		// Format the current date using the specified format
		String formattedDate = currentDate.format(formatter);
		head.setJobDate(formattedDate);

		JobLogHead save = headerRepo.save(head);

		return save.getLogId();

	}

	public void saveJobDetails(Long logId, String logLevel, String logMsg) {
		JobLogDetail jobLogDetail = new JobLogDetail();
		jobLogDetail.setLogId(logId);
		jobLogDetail.setLogLevel(logLevel);
		jobLogDetail.setLogMessage(logMsg);

		detailRepo.save(jobLogDetail);

	}

	@Transactional
	public void updateStatusHeader(String status, Long logId) {
		headerRepo.updateStatus(status, logId);
	}

}
