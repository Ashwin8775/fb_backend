package com.iocl.fb.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.scheduling.support.CronExpression;

import com.iocl.fb.dto.CronInputDto;
import com.iocl.fb.dto.CronResponseDto;
import com.iocl.fb.scheduling.DynamicSchedulingConfig;
import com.iocl.fb.scheduling.JobEntity;
import com.iocl.fb.scheduling.JobScheduleRepo;

@RestController
@RequestMapping("/cron")
public class CronController {

	@Autowired
	JobScheduleRepo jobInfoRepo;

	@Autowired
	private DynamicSchedulingConfig dynamicSchedulingConfig;

	@GetMapping(value = "/allJobs", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Map<String, Object>> getAllJobs() {

		List<JobEntity> jobInfos = jobInfoRepo.findAll();

		if (jobInfos.size()>0) {
			return jobInfos.stream().map(apiInfo -> {
				Map<String, Object> jobInfoMap = new HashMap<>();
				jobInfoMap.put("jobId", apiInfo.getJobId());
				jobInfoMap.put("jobName", apiInfo.getJobName());
				jobInfoMap.put("status", apiInfo.getStatus());
				jobInfoMap.put("cronTime", apiInfo.getCronTime());
				return jobInfoMap;
			}).collect(Collectors.toList());
		} else {
			return new ArrayList<>();
		}
	}

	@PostMapping(value = "/changeJobStatus")
	public ResponseEntity<CronResponseDto> chnageJobStatus(@RequestBody CronInputDto cronReq) {

		Optional<JobEntity> jobInfo = jobInfoRepo.findById(cronReq.getJobId());

		if (jobInfo.isPresent()) {
			JobEntity jobDets = jobInfo.get();
			if (isValidCronExpression(cronReq.getCronExpression())) {
				jobDets.setStatus(cronReq.getStatus());
				jobDets.setCronTime(cronReq.getCronExpression());
				jobInfoRepo.save(jobDets);

				dynamicSchedulingConfig.configureTasks();

				return new ResponseEntity<>(new CronResponseDto("SUCCESS",
						"Job Details updated Successfully for Job Id = " + cronReq.getJobId()), HttpStatus.OK);
			} else {

				return new ResponseEntity<>(
						new CronResponseDto("FAILURE",
								"Invalid Cron Expression.Please Correct It First" + cronReq.getJobId()),
						HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<>(new CronResponseDto("FAILURE", "Please Enter Valid Job Id To be Updated"),
					HttpStatus.BAD_REQUEST);
		}

	}

	private static boolean isValidCronExpression(String cronExpression) {
		return CronExpression.isValidExpression(cronExpression);
	}
}
