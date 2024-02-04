package com.iocl.fb.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.iocl.fb.config.JwtTokenProvider;
import com.iocl.fb.constatnts.ConstantDetails;
import com.iocl.fb.dto.AdminApprovalReqDto;
import com.iocl.fb.dto.ApprovalReponseDto;
import com.iocl.fb.dto.ApprovalReq;
import com.iocl.fb.dto.ApprovaldetailsDto;
import com.iocl.fb.dto.CategoryBean;
import com.iocl.fb.dto.ExhibitDto;
import com.iocl.fb.dto.GenerateTakeoverPdfDto;
import com.iocl.fb.dto.LocalityDto;
import com.iocl.fb.dto.OccupancyDetailsDTO;
import com.iocl.fb.dto.ResponseDto;
import com.iocl.fb.entities.CategoryDeTailsFlat;
import com.iocl.fb.entities.FlatOccupDet;
import com.iocl.fb.entities.FlatOccupDetId;
import com.iocl.fb.entities.ItemDetails;
import com.iocl.fb.entities.ItemManagement;
import com.iocl.fb.entities.ResidentSize;
import com.iocl.fb.exception.EmailContentNotFoundException;
import com.iocl.fb.jasper.ReportService;
import com.iocl.fb.model.ExhibitLlocResp;
import com.iocl.fb.repository.CategoryDetailsRepo;
import com.iocl.fb.repository.FbHouseRepo;
import com.iocl.fb.repository.FbReqDetRepo;
import com.iocl.fb.repository.FbReqHdrRepository;
import com.iocl.fb.repository.FlatOccupRepo;
import com.iocl.fb.repository.ItemDetailsRepo;
import com.iocl.fb.repository.ItemMgmRepo;
import com.iocl.fb.repository.Master2BhkRepo;
import com.iocl.fb.repository.ProcedureCaller;
import com.iocl.fb.repository.ResidRepo;
import com.iocl.fb.service.ExhibitFrontService;

/**
 * @author t_Salian
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	FbReqHdrRepository fbreqHdrRepo;

	@Autowired
	FbReqDetRepo fbreqDetRepo;

	@Autowired
	FlatOccupRepo flatOccRepo;

	@Autowired
	FbHouseRepo houseRepo;

	@Autowired
	Master2BhkRepo master2bhkrepo;

	@Autowired
	ReportService reportServ;

	@Autowired
	JwtTokenProvider tokenPerv;

	@Autowired
	ResidRepo residRepo;

	@Autowired
	ItemDetailsRepo itemDetRepo;

	@Autowired
	CategoryDetailsRepo catRepo;

	@Autowired
	ItemMgmRepo itemMgmRepo;

	@Autowired
	ProcedureCaller procCall;

	@Autowired
	ExhibitFrontService exhibitServ;

	@PostMapping("/aprovalList")

	public List<ApprovalReponseDto> approvalList(@RequestBody ApprovalReq approvalReq) {

		List<Integer> itemStatus = Arrays.asList(ConstantDetails.FLAT_REQ_CANCELED,
				ConstantDetails.FLAT_REQ_AUTO_CANCEL);

		List<ApprovalReponseDto> pendingApprovalList = fbreqHdrRepo.pendingApprovalList(approvalReq.getEmpCode(),
				approvalReq.getFormattedFromDate(), approvalReq.getFormattedToDate(), itemStatus,
				ConstantDetails.FLAT_REQ_PENDING);

		return pendingApprovalList;

	}

	@GetMapping("/requestDetails")
	public ApprovaldetailsDto requestDetails(@RequestParam Long reqId) {

		ApprovaldetailsDto approvalDets = fbreqHdrRepo.approvalDets(reqId);

		return approvalDets;

	}

	@PostMapping("/saveAdminReq")
	@Transactional
	public List<ApprovalReponseDto> saveAdminReq(@RequestBody AdminApprovalReqDto reqDto) {

		fbreqHdrRepo
				.updateApprovalDets(
						reqDto.getRdbtn_app_rej().equalsIgnoreCase("A") ? ConstantDetails.FLAT_REQ_APPROVED_BY_ADMIN
								: ConstantDetails.FLAT_REQ_REJECTED_BY_ADMIN,
						reqDto.getRemarks_admin(), reqDto.getReqId());

		fbreqDetRepo.updateApprovalDets(
				reqDto.getRdbtn_app_rej().equalsIgnoreCase("A") ? ConstantDetails.FLAT_REQ_APPROVED_BY_ADMIN
						: ConstantDetails.FLAT_REQ_REJECTED_BY_ADMIN,
				reqDto.getReqId());

		List<Integer> itemStatus = Arrays.asList(ConstantDetails.FLAT_REQ_CANCELED,
				ConstantDetails.FLAT_REQ_AUTO_CANCEL);

		List<ApprovalReponseDto> pendingApprovalList = fbreqHdrRepo.pendingApprovalList(reqDto.getAdmin_empCode(),
				reqDto.getFormattedFromDate(), reqDto.getFormattedToDate(), itemStatus,
				ConstantDetails.FLAT_REQ_PENDING);

		return pendingApprovalList;
	}

	// Special Admin API's
	@GetMapping("/occupiedList")
	public List<OccupancyDetailsDTO> occupiedList(@RequestParam String paramType, @RequestParam Long value) {

		List<OccupancyDetailsDTO> findOccupancyDetails = flatOccRepo.findOccupancyDetails(paramType, value,
				ConstantDetails.FLAT_REQ_OCCUPIED);

		return findOccupancyDetails;
	}

	@PostMapping("/forceVacant")
	@Transactional
	public ResponseEntity<ResponseDto> forceVacant(@RequestParam("takoverFile") MultipartFile takeoverFile,
			@RequestParam("takoverFileName") String takoverFileName, @RequestParam("reqId") Long reqId,
			@RequestParam("houseId") Long houseId, @RequestParam("localityCode") Integer localityCode,
			@RequestParam("vacDate") Date vacDate, @RequestParam("remarks") String remarks,
			@RequestParam("empCode") String empCode) {

		byte[] fileContent = null;
		try {
			fileContent = takeoverFile.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
			// Handle the exception
		}

		houseRepo.updateStatus(ConstantDetails.FLAT_LONG_MAINTENANCE_REDEVELOPMENT, houseId);

		fbreqHdrRepo.updateDets(ConstantDetails.FLAT_REQ_VACATED_FINAL, reqId);

		fbreqDetRepo.updateFinalDets(ConstantDetails.FLAT_REQ_VACATED_FINAL, reqId, localityCode);

		flatOccRepo.updateFlatOccupDets(vacDate, empCode, remarks, reqId, houseId);

		FlatOccupDet flatOccupDet = flatOccRepo.findById(new FlatOccupDetId(houseId, reqId))
				.orElseThrow(() -> new EntityNotFoundException("FlatOccupDet not found with requestId: " + reqId));
		flatOccupDet.setTakeoverFileName(takoverFileName);
		flatOccupDet.setTakeoverFile(fileContent);
		flatOccRepo.save(flatOccupDet);

		return new ResponseEntity<>(new ResponseDto("SUCCESS", HttpStatus.OK.value()), HttpStatus.OK);
	}

	@GetMapping("/fetchItemDetails")
	public List<CategoryBean> fetchItemDetails(@RequestParam Long locCode) {

		List<CategoryBean> arrayList = new ArrayList<>();

		List<ItemDetails> itemDetailsList = itemDetRepo.findByLocCodeAndResidScodeAndStatusOrderByTypeAscSnoAsc(locCode,
				102l, "A");

		Map<String, Integer> divisionCountMap = new HashMap<>();
		int i = 1;
		for (ItemDetails item : itemDetailsList) {
			String category = item.getCategory().getDivision() + " - " + item.getCategory().getCategoryName();
			String unit = item.getUnit();

			// Check if a row needs to be added before adding the current division data
			addRowBefore(arrayList, divisionCountMap, item.getCategory().getDivision(), category);

			// Add the current data to the result list
			arrayList.add(new CategoryBean(i++, item.getSno(), item.getItem(), item.getQty(), item.getResidScode(),
					item.getType(), unit));

			// Increment the division count
			divisionCountMap.put(item.getCategory().getDivision(),
					divisionCountMap.getOrDefault(item.getCategory().getDivision(), 0) + 1);

		}

		return arrayList;
	}

	private static void addRowBefore(List<CategoryBean> resultList, Map<String, Integer> divisionCountMap,
			String currentDivision, String category) {
		int divisionCount = divisionCountMap.getOrDefault(currentDivision, 0);

		if (divisionCount == 0) {
			resultList.add(new CategoryBean(null, category, ""));
		}
	}

	@PostMapping("/genTakeoverPdf")
	@Transactional
	public ResponseEntity<ResponseDto> generateTakeOverPdf(@RequestBody GenerateTakeoverPdfDto pdfDto,
			HttpServletRequest request) {

		String resolveToken = tokenPerv.resolveToken(request);

		String username = tokenPerv.getUsername(resolveToken);

		if (Long.parseLong(pdfDto.getEmpCode()) != Long.parseLong(username)) {
			return new ResponseEntity<>(
					new ResponseDto("User is Not Accessing from the Application", HttpStatus.BAD_REQUEST.value()),
					HttpStatus.BAD_REQUEST);
		}

		for (CategoryBean bean : pdfDto.getItemDets()) {

			if (bean.getSrno() != null) {
				ItemManagement itemManagement = new ItemManagement();
				itemManagement.setLocCode(pdfDto.getOccLocId());
				itemManagement.setEmpCode(Long.parseLong(pdfDto.getOccempcode()));
				itemManagement.setRequestId(pdfDto.getRequestId());
				itemManagement.setItemSrNo(bean.getSrno());
				itemManagement.setQty(bean.getQty());
				itemManagement.setUpdatedBy(pdfDto.getEmpCode());
				itemManagement.setUpdatedOn(new Timestamp(System.currentTimeMillis()));

				itemMgmRepo.save(itemManagement);
			}

		}

		try {
			String claimData = reportServ.claimData(pdfDto);
			return new ResponseEntity<>(new ResponseDto(claimData, HttpStatus.OK.value()), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(new ResponseDto(e.getMessage(), HttpStatus.BAD_REQUEST.value()),
					HttpStatus.BAD_REQUEST);
		}

	}

	@GetMapping("/residList")
	public List<ResidentSize> residList() {
		return residRepo.findAll();

	}

	@GetMapping("/categList")
	public Map<String, Object> categList() {
		Map<String, Object> mapData = new HashMap<>();
		Long lastSno = itemDetRepo.findMaxSno();
		Long newSno = (lastSno != null) ? lastSno + 1 : 1;

		mapData.put("catList", catRepo.findAll());
		mapData.put("sno", newSno);

		return mapData;

	}

	@GetMapping("/itemDetailsResid")
	public List<ItemDetails> itemDetailsList(@RequestParam Long residCode, @RequestParam Long locId) {

		return itemDetRepo.findByLocCodeAndResidScodeAndStatusOrderByTypeAscSnoAsc(locId, residCode, "A");

	}

	@GetMapping("/updateItemStatus")
	@Transactional
	public List<ItemDetails> updateItemStatus(@RequestParam Long sno, @RequestParam Long residCode,
			@RequestParam Long locId) {
		try {
			itemDetRepo.updateBySno("D", sno);
			return itemDetailsList(residCode, locId);
		} catch (Exception e) {
			throw new RuntimeException("Failed to update item status: " + e.getMessage());
		}

	}

	@PostMapping("/saveItemDets")
	@Transactional
	public List<ItemDetails> saveItemDetails(@RequestBody ItemDetails item) {
		// Fetch CategoryDeTailsFlat separately
		CategoryDeTailsFlat category = catRepo.findById(item.getType()).orElse(null);
		item.setCategory(category);
		itemDetRepo.save(item);

		return itemDetailsList(item.getResidScode(), item.getLocCode());

	}

	// EXHIBIT INDIVIDUAL
	@GetMapping("/vacantFlats")
	public List<LocalityDto> getVacantflatDets() {
		return houseRepo.findVacantLocalities(1);
	}

	@PostMapping("/exhibitPreferences")
	public ResponseEntity<?> exhibitPreferences(@RequestBody ExhibitDto exhibitReq) {

		List<ExhibitLlocResp> locresp = new ArrayList<>();
		for (LocalityDto loc : exhibitReq.getLocalities()) {
			try {
				locresp.addAll(exhibitServ.exhibitLocalities(loc));
			} catch (EmailContentNotFoundException e) {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
			}
		}

		return ResponseEntity.ok(locresp);

	}

}
