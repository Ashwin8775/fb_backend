package com.iocl.fb.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iocl.fb.constatnts.ConstantDetails;
import com.iocl.fb.dto.AllocationCounts;
import com.iocl.fb.dto.RequestDetailsDto;
import com.iocl.fb.entities.AllotmentDetails;
import com.iocl.fb.entities.CategoryRatio;
import com.iocl.fb.entities.FbRequestHeader;
import com.iocl.fb.entities.House;
import com.iocl.fb.entities.Locality;
import com.iocl.fb.entities.RunHeader;
import com.iocl.fb.repository.AllotmentDetailsRepo;
import com.iocl.fb.repository.CategoryRatioRepo;
import com.iocl.fb.repository.FbHouseRepo;
import com.iocl.fb.repository.FbReqDetRepo;
import com.iocl.fb.repository.FbReqHdrRepository;
import com.iocl.fb.repository.LocalityRepo;
import com.iocl.fb.repository.RunHeaderRepo;

@Service
public class AllotmentService {

	@Autowired
	FbHouseRepo houseRepo;

	@Autowired
	FbReqDetRepo reqDetRepo;

	@Autowired
	FbReqHdrRepository reqHdrRepo;

	@Autowired
	RunHeaderRepo runHeadRepo;

	@Autowired
	AllotmentDetailsRepo allotRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	LocalityRepo localityRepo;

	@Autowired
	CategoryRatioRepo catRatioRepo;

	@Autowired
	JobLogService jobLogs;

	@Transactional
	public long doAllotment(RequestDetailsDto reqs, long runId) {
		
		long houseIdAllot = 0l;

		long[] longArray = getPrefOrderList(reqs.getPrefOrder());

		// Using Iterator to ensure sequential processing
		Iterator<Long> iterator = Arrays.stream(longArray).iterator();

		while (iterator.hasNext()) {
			long houseId = iterator.next();

			Optional<House> houseData = houseRepo.findById(houseId);

			House house = houseData.get();
			if (house.getStatus() == ConstantDetails.FLAT_VACANT) {
				if (runId != 0l)
					jobLogs.saveJobDetails(runId, "INFO",
							"House Id:" + houseId + " is being Alloted to RequestId:" + reqs.getRequestId());

				houseRepo.updateStatus(ConstantDetails.FLAT_ALLOTTED, houseId);
				reqDetRepo.updateStatus(ConstantDetails.FLAT_REQ_ALLOTTED, reqs.getRequestId(), reqs.getLocalityCode());
				reqHdrRepo.updateDets(ConstantDetails.FLAT_REQ_ALLOTTED, reqs.getRequestId());
				
				Optional<RunHeader> runHeadOpt = runHeadRepo.findByLocCodeAndLocalityCodeAndEndDate(reqs.getLocCode(),
						reqs.getLocalityCode(), LocalDate.now());

				if (runHeadOpt.isPresent()) {
					RunHeader runHeader = runHeadOpt.get();
					runHeader.setAllotedFlats(runHeader.getAllotedFlats() + 1);
					runHeadRepo.save(runHeader);
				}

				saveAllotmentDetails(reqs.getRequestId(), runId, reqs.getLocalityCode(), houseId);

				// To refresh the entity explicitily
				entityManager.refresh(house);

				localityRepo.updateCategoryCount(reqs.getAppCat(), reqs.getLocalityCode());
				
				houseIdAllot=houseId;

				break;

			}

		}
		
		return houseIdAllot;
		
		

	}

	private static long[] getPrefOrderList(String csvValues) {
		// Split the CSV string by commas, convert each value to long, and collect into
		// an array
		return Arrays.stream(csvValues.split(",")).mapToLong(value -> {
			try {
				return Long.parseLong(value.trim());
			} catch (NumberFormatException e) {
				// Handle the case where a value is not a valid long
				System.err.println("Error parsing long value: " + value);
				return 0L; // Default value or another appropriate value
			}
		}).toArray();
	}

	public void saveAllotmentDetails(Long requestId, Long runId, int localityCode, long houseId) {
		Optional<FbRequestHeader> reqHdrOpt = reqHdrRepo.findById(requestId);
		FbRequestHeader fbRequestHeader = reqHdrOpt.get();

		AllotmentDetails allotmentDetails = new AllotmentDetails();
		allotmentDetails.setRequestId(requestId);
		allotmentDetails.setRunId(runId);
		allotmentDetails.setEmpCode(fbRequestHeader.getEmpCode());
		allotmentDetails.setEmpName(fbRequestHeader.getEmpName());
		allotmentDetails.setGrade(fbRequestHeader.getGrade());
		allotmentDetails.setDesignation(fbRequestHeader.getDesig());
		allotmentDetails.setAppCat(fbRequestHeader.getAppCat());
		allotmentDetails.setHouseId(houseId);
		allotmentDetails.setLocalityCode(localityCode);
		allotmentDetails.setWlOnAllotment(0l);
		allotmentDetails.setOverridingFlag(10);
		allotmentDetails.setAllotmentDate(LocalDate.now());
		allotmentDetails.setAcceptFlag(ConstantDetails.ALLOTMENT_ACCEPTED);
		allotmentDetails.setCancelFlag(0);

		allotRepo.save(allotmentDetails);

	}

	public AllocationCounts fetchCategoryToBeAlloted(Integer locality) {
		int lastAllotedCat = 0;
		Optional<Locality> locDetsOpt = localityRepo.findById(locality);
		Map<Integer, Integer> finalallocDetails = new HashMap<>();

		if (locDetsOpt.isPresent()) {
			Locality locDets = locDetsOpt.get();
			lastAllotedCat = locDets.getLastAllottedCat();
			int lastAllotedCount = locDets.getLastAllotedCount();

			Map<Integer, Integer> allocDetails = new HashMap<>();
			allocDetails.put(lastAllotedCat, lastAllotedCount);

			Map<Integer, Integer> catCodeToRatioMap = catRatioRepo.findByLocalityCodeAndStatus(locality, "A").stream()
					.collect(Collectors.toMap(CategoryRatio::getCatCode, CategoryRatio::getRatio));

			catCodeToRatioMap.keySet().forEach(catCode -> {
				if (catCodeToRatioMap.get(catCode) > 0) {
					allocDetails.putIfAbsent(catCode, 0);
				}

			});

			Integer ratio = catCodeToRatioMap.get(lastAllotedCat);

			if (allocDetails.get(lastAllotedCat) < ratio) {

			} else {
				allocDetails.put(lastAllotedCat, 0);
				lastAllotedCat = getNextCategoryCode(lastAllotedCat, catCodeToRatioMap.keySet());

			}
			finalallocDetails = allocDetails;
		}

		return new AllocationCounts(lastAllotedCat, finalallocDetails);
	}

	private int getNextCategoryCode(int currentCatCode, Set<Integer> categoryCodes) {
		List<Integer> sortedCodes = new ArrayList<>(categoryCodes);
		Collections.sort(sortedCodes);

		int index = sortedCodes.indexOf(currentCatCode);
		if (index == sortedCodes.size() - 1) {
			// If the current category is the last one, wrap around to the first category
			return sortedCodes.get(0);
		} else {
			// Otherwise, move to the next category
			return sortedCodes.get(index + 1);
		}
	}

}
