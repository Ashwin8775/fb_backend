package com.iocl.fb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.fb.dto.FlatMgmReq;
import com.iocl.fb.dto.HouseDetailsDTO;
import com.iocl.fb.dto.ResponseDto;
import com.iocl.fb.entities.FlatMgm;
import com.iocl.fb.entities.House;
import com.iocl.fb.model.HouseDetsReqDto;
import com.iocl.fb.repository.FbHouseRepo;
import com.iocl.fb.repository.FlatMgmRepo;

/**
 * @author t_Salian
 *
 */
@RestController
@RequestMapping("/manage")
@CrossOrigin(origins = "*")
public class ManageFlatController {

	@Autowired
	FbHouseRepo houseRepo;

	@Autowired
	FlatMgmRepo flatMgmRepo;

	@PostMapping("/fetchHouses")
	public List<HouseDetailsDTO> fetchHouses(@RequestBody FlatMgmReq flatReq) {

		System.out.println("Test = " + flatReq);

		return houseRepo.findHouseDetailsByAdmin(flatReq.getEmpCode(), flatReq.getLocation() == -1 ? null : flatReq.getLocation(),
	            flatReq.getLocality() == -1 ? null : flatReq.getLocality(),
	                    flatReq.getColony() == -1 ? null : flatReq.getColony(),
	                    flatReq.getStatus() == -1 ? null : flatReq.getStatus());
	}

	@PostMapping("/statusChange")
	@Transactional
	public ResponseEntity<ResponseDto> statusChange(@RequestBody HouseDetsReqDto houseReq) {
		try {
			Optional<House> houseDets = houseRepo.findById(houseReq.getHouseId());

			if (houseDets.isPresent()) {

				FlatMgm flatMgm = new FlatMgm();
				flatMgm.setHouseId(houseReq.getHouseId());
				flatMgm.setCurrStatus(houseReq.getStatus());
				flatMgm.setPrevStatus(houseDets.get().getStatus());
				flatMgm.setCurrResidSize(houseReq.getResidSCode());
				flatMgm.setPrevResidSize(houseReq.getResidSCode());
				flatMgm.setUpdatedBy(houseReq.getEmpCode());
				flatMgm.setUpdatedOn(new Date());
				flatMgm.setRemarks(houseReq.getRemarks());

				flatMgmRepo.save(flatMgm);

				houseRepo.updateStatus(houseReq.getStatus(), houseReq.getHouseId());

				return new ResponseEntity<>(
						new ResponseDto("Status Updated Successfully for House No = " + houseReq.getHouseNo(), 1),
						HttpStatus.OK);

			} else {
				return new ResponseEntity<>(new ResponseDto("House Id is not Present in Db", 0), HttpStatus.OK);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(
					new ResponseDto("Staus cannot be updated because = " + e.getLocalizedMessage(), 0),
					HttpStatus.BAD_REQUEST);
		}

	}

}
