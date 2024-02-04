package com.iocl.fb.jobs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iocl.fb.constatnts.ConstantDetails;
import com.iocl.fb.dto.AllocationCounts;
import com.iocl.fb.dto.AllocationInfo;
import com.iocl.fb.dto.LocalityDto;
import com.iocl.fb.dto.RequestDetailsDto;
import com.iocl.fb.repository.FbHouseRepo;
import com.iocl.fb.repository.FbReqDetRepo;
import com.iocl.fb.service.AllotmentService;
import com.iocl.fb.service.JobLogService;

@Service

public class AllotmentServiceJob {

	@Autowired
	FbHouseRepo houseRepo;

	@Autowired
	FbReqDetRepo reqDetRepo;

	@Autowired
	AllotmentService allotService;

	@Autowired
	JobLogService jobLogs;

	private static Long HEADER_LOG_ID = 0L;

	public void allotmentJobSchedule() {

		HEADER_LOG_ID = jobLogs.saveJobHeader(2l, "P", null);

		jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Allotment Job Started At " + LocalDateTime.now());

		List<LocalityDto> vacantLocalities = houseRepo.findVacantLocalities(1);
		jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO",
				"Fetched " + vacantLocalities.size() + " Localities where there are vacant flats");

		for (LocalityDto dto : vacantLocalities) {

			jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Processing " + dto.getLocalityName() + " Locality");
			int vacantFlats = dto.getVacant().intValue();

			jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO",
					"Total Number of flats vacant in " + dto.getLocalityName() + " Locality is " + vacantFlats);
			for (int i = 0; i < vacantFlats; i++) {
				AllocationCounts fetchCategoryToBeAlloted = allotService
						.fetchCategoryToBeAlloted(dto.getLocalityCode());

				jobLogs.saveJobDetails(HEADER_LOG_ID, "DEBUG",
						"Category to Be  Alloted Flat is " + fetchCategoryToBeAlloted.getNextcategory());

				List<RequestDetailsDto> reqDets = getRequestDetailsByCategory(dto.getLocalityCode(),
						fetchCategoryToBeAlloted.getNextcategory());
				boolean currStatus = false;
				if (reqDets.isEmpty()) {
					currStatus = true;
					jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO",
							"No Request Present with category " + fetchCategoryToBeAlloted.getNextcategory());
					// If no requests found for the current category, try other categories
					for (Map.Entry<Integer, Integer> entry : fetchCategoryToBeAlloted.getAllocDetails().entrySet()) {
						int otherCategory = entry.getKey();
						if (otherCategory != fetchCategoryToBeAlloted.getNextcategory()) {
							reqDets = getRequestDetailsByCategory(dto.getLocalityCode(), otherCategory);
							if (reqDets.isEmpty()) {
								break; // Break the loop if requests found for other category
							}
						}
					}
				}
				if (!reqDets.isEmpty()) {
					if (currStatus)
						jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO",
								"Category to Be  Alloted Flat is " + fetchCategoryToBeAlloted.getNextcategory());

					// Using Iterator to ensure sequential processing
					Iterator<RequestDetailsDto> iterator = reqDets.iterator();
					while (iterator.hasNext()) {
						RequestDetailsDto reqs = iterator.next();
						jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Processing Request Id:" + reqs.getRequestId()
								+ " with Preference Order(" + reqs.getPrefOrder() + ")");
						long doAllotment = allotService.doAllotment(reqs, HEADER_LOG_ID);
						if (doAllotment == 0l)
							jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO",
									"No Allotment Done for Request Id :" + reqs.getRequestId());

					}

				}

			}

		}

		jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Allotment Job Ended At " + LocalDateTime.now());

		jobLogs.updateStatusHeader("S", HEADER_LOG_ID);

	}

	private List<RequestDetailsDto> getRequestDetailsByCategory(int localityCode, int category) {
		return reqDetRepo.findRequestDetailsByLocalityAndStatus(1, localityCode,
				ConstantDetails.FLAT_REQ_ALLOTMENT_ACCEPTED, category);
	}

}
