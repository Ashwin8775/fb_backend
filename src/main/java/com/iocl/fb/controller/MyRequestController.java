package com.iocl.fb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.fb.constatnts.ConstantDetails;
import com.iocl.fb.dto.ApprovalReponseDto;
import com.iocl.fb.dto.ApprovalReq;
import com.iocl.fb.dto.ApprovaldetailsDto;
import com.iocl.fb.dto.HouseListDto;
import com.iocl.fb.dto.PreferencesReqDto;
import com.iocl.fb.dto.ResponseDto;
import com.iocl.fb.repository.FbHouseRepo;
import com.iocl.fb.repository.FbReqDetRepo;
import com.iocl.fb.repository.FbReqHdrRepository;

/**
 * @author t_Salian
 *
 */
@RestController
@RequestMapping("/myRequest")
@CrossOrigin(origins = "*")
public class MyRequestController {

	@Autowired
	FbReqHdrRepository fbreqHdrRepo;

	@Autowired
	FbReqDetRepo fbreqDetRepo;

	@Autowired
	FbHouseRepo houseRepo;

	@PostMapping("/processList")
	public List<ApprovalReponseDto> processList(@RequestBody ApprovalReq approvalReq) {

		List<Integer> itemStatus = Arrays.asList(ConstantDetails.FLAT_REQ_CANCELED,
				ConstantDetails.FLAT_REQ_AUTO_CANCEL);
		List<Integer> status = Arrays.asList(ConstantDetails.FLAT_REQ_CANCELED,
				ConstantDetails.FLAT_REQ_REJECTED_MAX_REJECTION, ConstantDetails.FLAT_REQ_AUTO_CANCEL);

		List<ApprovalReponseDto> processList = fbreqHdrRepo.processAndCancelList(approvalReq.getEmpCode(),
				approvalReq.getFormattedFromDate(), approvalReq.getFormattedToDate(), itemStatus, status, false, false);

		return processList;

	}

	@GetMapping("/requestDetails")
	public ApprovaldetailsDto requestDetails(@RequestParam Long reqId) {

		List<Long> prefOrdrList = new ArrayList<>();

		ApprovaldetailsDto approvalDets = fbreqHdrRepo.approvalDets(reqId);

		Integer itemStatus = approvalDets.getItemStatus();

		if (itemStatus == ConstantDetails.FLAT_REQ_OFFERED || itemStatus == ConstantDetails.FLAT_REQ_PREFERENCE_SELECTED
				|| itemStatus == ConstantDetails.FLAT_REQ_ALLOTMENT_REJECTED
				|| itemStatus == ConstantDetails.FLAT_REQ_ALLOTMENT_ACCEPTED) {
			approvalDets.setShowTableStatus(true);
			if (itemStatus == ConstantDetails.FLAT_REQ_ALLOTMENT_REJECTED
					|| itemStatus == ConstantDetails.FLAT_REQ_ALLOTMENT_ACCEPTED) {
				approvalDets.setShowUpdateStatus(true);
			} else {
				approvalDets.setShowUpdateStatus(false);
			}

		} else {
			approvalDets.setShowTableStatus(false);
		}

		String prefOrder = approvalDets.getPrefOrder();
		if (prefOrder != null) {
			String[] stringValues = prefOrder.split(",");
			prefOrdrList = Arrays.stream(stringValues).map(String::trim) // Trim each element to remove leading/trailing
																			// whitespaces
					.filter(s -> !s.isEmpty()) // Filter out empty strings
					.map(Long::valueOf).collect(Collectors.toList());
		}

		if (prefOrdrList.size() >= 1) {

			List<HouseListDto> findHousesByLocalityAndIds = houseRepo
					.findHousesByLocalityAndIds(approvalDets.getLocalityCode(), prefOrdrList);

			approvalDets.setHouseList(sortTheList(findHousesByLocalityAndIds, prefOrdrList));
			if (approvalDets.isShowTableStatus() && prefOrdrList.size() > 1)
				approvalDets.setShowPreferenceStatus(true);
			else
				approvalDets.setShowPreferenceStatus(false);
		}

		return approvalDets;

	}

	@PostMapping("/approvedList")
	public List<ApprovalReponseDto> approvedList(@RequestBody ApprovalReq approvalReq) {

		List<Integer> itemStatus = Arrays.asList(ConstantDetails.FLAT_REQ_CANCELED,
				ConstantDetails.FLAT_REQ_AUTO_CANCEL);

		List<ApprovalReponseDto> processList = fbreqHdrRepo.appRejList(approvalReq.getEmpCode(),
				approvalReq.getFormattedFromDate(), approvalReq.getFormattedToDate(),
				ConstantDetails.FLAT_REQ_APPROVED_BY_ADMIN, itemStatus);

		return processList;

	}

	@PostMapping("/rejectedList")
	public List<ApprovalReponseDto> rejectedList(@RequestBody ApprovalReq approvalReq) {

		List<Integer> itemStatus = Arrays.asList(ConstantDetails.FLAT_REQ_CANCELED,
				ConstantDetails.FLAT_REQ_AUTO_CANCEL);

		List<ApprovalReponseDto> processList = fbreqHdrRepo.appRejList(approvalReq.getEmpCode(),
				approvalReq.getFormattedFromDate(), approvalReq.getFormattedToDate(),
				ConstantDetails.FLAT_REQ_REJECTED_BY_ADMIN, itemStatus);

		return processList;

	}

	@PostMapping("/cancelList")
	public List<ApprovalReponseDto> cancelList(@RequestBody ApprovalReq approvalReq) {

		List<Integer> itemStatus = Arrays.asList(ConstantDetails.FLAT_REQ_CANCELED,
				ConstantDetails.FLAT_REQ_REJECTED_MAX_REJECTION, ConstantDetails.FLAT_REQ_AUTO_CANCEL);
		List<Integer> status = Arrays.asList(ConstantDetails.FLAT_REQ_CANCELED,
				ConstantDetails.FLAT_REQ_REJECTED_MAX_REJECTION, ConstantDetails.FLAT_REQ_AUTO_CANCEL);

		List<ApprovalReponseDto> processList = fbreqHdrRepo.processAndCancelList(approvalReq.getEmpCode(),
				approvalReq.getFormattedFromDate(), approvalReq.getFormattedToDate(), itemStatus, status, true, true);

		return processList;

	}

	@PostMapping("/updatePreferences")
	@Transactional
	public ResponseEntity<ResponseDto> updatePrefrenceStatus(@RequestBody PreferencesReqDto prefReq) {
		int status = 0;

		if (prefReq.getStatus().equalsIgnoreCase("A"))
			status = ConstantDetails.FLAT_REQ_ALLOTMENT_ACCEPTED;
		else
			status = ConstantDetails.FLAT_REQ_ALLOTMENT_REJECTED;

		String prefOrder = prefReq.getHouseList().stream().filter(house -> house.getPriority() > 0)
				.sorted(Comparator.comparingInt(HouseListDto::getPriority)).map(HouseListDto::getHouseId)
				.map(String::valueOf).collect(Collectors.joining(","));

		fbreqDetRepo.updatePrefDets(status, prefOrder, prefReq.getReqId(), prefReq.getLocalityCode());

		fbreqHdrRepo.updateDets(status, prefReq.getReqId());

		return new ResponseEntity<>(
				new ResponseDto("Your Request Has been processed against Request Id : " + prefReq.getReqId(),
						HttpStatus.OK.value()),
				HttpStatus.OK);

	}

	private List<HouseListDto> sortTheList(List<HouseListDto> houseList, List<Long> prefOrder) {
		// Sort the houseList based on prefOrder using streams and set priority
		List<HouseListDto> sortedHouseList = prefOrder.stream().map(houseId -> houseList.stream()
				.filter(house -> house.getHouseId() == houseId.intValue()).findFirst().orElse(null))
				.filter(house -> house != null).collect(Collectors.toList());

		// Set priority values in sequence
		for (int i = 0; i < sortedHouseList.size(); i++) {
			sortedHouseList.get(i).setPriority(i + 1);
		}

		return sortedHouseList;
	}

}
