package com.iocl.fb.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.fb.constatnts.ConstantDetails;
import com.iocl.fb.dto.ApprovalReponseDto;
import com.iocl.fb.dto.ApprovalReq;
import com.iocl.fb.entities.FbRequestDetails;
import com.iocl.fb.entities.RequestRejection;
import com.iocl.fb.repository.FbReqDetRepo;
import com.iocl.fb.repository.FbReqHdrRepository;
import com.iocl.fb.repository.FbReqRejectionRepo;

/**
 * @author t_Salian
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/withdraw")
public class WithdrawController {

	@Autowired
	FbReqHdrRepository fbreqHdrRepo;

	@Autowired
	FbReqDetRepo fbreqDetRepo;

	@Autowired
	FbReqRejectionRepo fbRejectRepo;

	@PostMapping("/cancelList")
	public List<ApprovalReponseDto> rejectedList(@RequestBody ApprovalReq approvalReq) {

		List<Integer> itemStatus = Arrays.asList(ConstantDetails.FLAT_REQ_CANCELED,
				ConstantDetails.FLAT_REQ_REJECTED_MAX_REJECTION, ConstantDetails.FLAT_REQ_AUTO_CANCEL);
		List<Integer> status = Arrays.asList(ConstantDetails.FLAT_REQ_PENDING,
				ConstantDetails.FLAT_REQ_APPROVED_BY_ADMIN, ConstantDetails.FLAT_REQ_ALLOTMENT_REJECTED,
				ConstantDetails.FLAT_REQ_OFFERED, ConstantDetails.FLAT_REQ_PREFERENCE_SELECTED,
				ConstantDetails.FLAT_REQ_ALLOTMENT_ACCEPTED);

		List<ApprovalReponseDto> processList = fbreqHdrRepo.processAndCancelList(approvalReq.getEmpCode(),
				approvalReq.getFormattedFromDate(), approvalReq.getFormattedToDate(), itemStatus, status, false, true);

		return processList;

	}

	@PostMapping("/cancelReq")
	@Transactional
	public ResponseEntity<List<ApprovalReponseDto>> saveReqStatus(@RequestBody ApprovalReq approvalReq) {

		Optional<FbRequestDetails> reqDets = fbreqDetRepo
				.findItemStatusByRequestIdAndLocalityCode(approvalReq.getRequestId(), approvalReq.getLocalityCode());

		if (reqDets.isPresent()) {
			RequestRejection requestRejection = new RequestRejection(approvalReq.getRequestId(),
					approvalReq.getLocalityCode(), approvalReq.getEmpCode(), approvalReq.getRemarks(),
					reqDets.get().getItemStatus());

			fbRejectRepo.save(requestRejection);

			// Detail Updation

			fbreqDetRepo.updateDets(ConstantDetails.FLAT_REQ_CANCELED, approvalReq.getRemarks(),
					approvalReq.getEmpCode(), approvalReq.getRequestId(), approvalReq.getLocalityCode());

			// Header Updation
			fbreqHdrRepo.updateDets(ConstantDetails.FLAT_REQ_CANCELED, approvalReq.getRequestId());

			return new ResponseEntity<>(rejectedList(approvalReq), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(rejectedList(approvalReq), HttpStatus.BAD_REQUEST);
		}

	}

}
