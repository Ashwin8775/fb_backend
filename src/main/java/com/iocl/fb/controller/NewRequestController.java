package com.iocl.fb.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.iocl.fb.dto.LocalityDto;
import com.iocl.fb.dto.NewReqDto;
import com.iocl.fb.entities.FbRequestHeader;
import com.iocl.fb.model.SaveReqProcResp;
import com.iocl.fb.repository.FbReqHdrRepository;
import com.iocl.fb.repository.IocLocationRepo;
import com.iocl.fb.repository.LocalityRepo;
import com.iocl.fb.repository.ProcedureCaller;

/**
 * @author t_Salian
 *
 */
@RestController
@RequestMapping("/newRequest")
@CrossOrigin(origins = "*")
public class NewRequestController {

	@Autowired
	IocLocationRepo iocLocRepo;

	@Autowired
	ProcedureCaller procCall;

	@Autowired
	LocalityRepo locRepo;

	@Autowired
	FbReqHdrRepository fbReqHdrRepo;

	@GetMapping("/reqcheck")
	public NewReqDto fetchReqPresent(@RequestParam String empCode) {

		NewReqDto newReqDto = new NewReqDto();

		FbRequestHeader reqhdrData = fbReqHdrRepo.findByEmpCodeAndAppCatAndStatus(Long.parseLong(empCode), 31, 50);

		if (reqhdrData == null) {
			newReqDto.setStatus(0);
			return newReqDto;
		} else {

			newReqDto.setRdbtn_own_acc(reqhdrData.getOwnAcco() == 22 ? "Y" : "N");
			newReqDto.setRemarks(reqhdrData.getRemarks());
			newReqDto.setLocationSel(2);
			newReqDto.setStatus(1);
			newReqDto.setReqId(String.valueOf(reqhdrData.getRequestId()));
			return newReqDto;
		}

	}

	@GetMapping("/locations")
	public List<LocalityDto> menuListDetails(@RequestParam int locCode, @RequestParam String grade) {

		List<Long> flatlist = new ArrayList<>(Arrays.asList(101L, 102L, 103L));

		List<String> seniorGradelist = Arrays.asList("AF", "AG", "AH", "AI");

		int lastIndex = flatlist.size() - 1;

		if (!seniorGradelist.contains(grade)) {
			flatlist.remove(lastIndex);
		}

		Integer findLocCodeByIocLocCode = iocLocRepo.findLocCodeByIocLocCode(locCode);

		return locRepo.findLocalityCodeAndlLocalityNameByLocCode(findLocCodeByIocLocCode, flatlist);

	}

	@PostMapping("/saveReqData")
	public ResponseEntity<SaveReqProcResp> saveRequestData(@RequestBody NewReqDto newReq) {

		Map<String, Object> inputParams = new HashMap<>();
		inputParams.put("I_REQ_FLAG", "N");
		inputParams.put("I_EMP_CODE", newReq.getEmpCode());
		inputParams.put("I_IOC_LOC_CODE", newReq.getIocLocCode());
		inputParams.put("I_GRADE", newReq.getGrade());
		inputParams.put("I_LOC_ID", newReq.getLocationSel());
		inputParams.put("I_OWN_ACC", newReq.getRdbtn_own_acc());
		inputParams.put("I_REMARKS", newReq.getRemarks());

		List<SaveReqProcResp> callSaveReqProcedure = procCall.callStoredProcedure("SAVE_REQ_DATA", inputParams,
				SaveReqProcResp.class);

		SaveReqProcResp saveReqProcResp = callSaveReqProcedure.get(0);

		if (saveReqProcResp.getReqId() == null || saveReqProcResp.getReqId().equalsIgnoreCase("0")) {
			return new ResponseEntity<>(saveReqProcResp, HttpStatus.OK);
		}

		return new ResponseEntity<>(saveReqProcResp, HttpStatus.OK);

	}

}
