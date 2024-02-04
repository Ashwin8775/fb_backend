package com.iocl.fb.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iocl.fb.constatnts.ConstantDetails;
import com.iocl.fb.dto.GlobalVariableDto;
import com.iocl.fb.dto.LocalityDto;
import com.iocl.fb.dto.LocationDTO;
import com.iocl.fb.repository.ColonyRepo;
import com.iocl.fb.repository.ColonyRepo.ColonyProjection;
import com.iocl.fb.repository.GlobalVariableRepo;
import com.iocl.fb.repository.LocalityRepo;
import com.iocl.fb.repository.LocationRepo;

/**
 * @author t_Salian
 *
 */
@RestController
@RequestMapping("/populate")
@CrossOrigin(origins = "*")
public class PopulateDropDownController {

	@Autowired
	LocationRepo locRepo;

	@Autowired
	LocalityRepo localityRepo;

	@Autowired
	ColonyRepo colonyRepo;

	@Autowired
	GlobalVariableRepo globalrepo;

	@GetMapping("/fetchLocation")
	public List<LocationDTO> fetchLocation(@RequestParam Long empCode) {
		return locRepo.findLocationsByAdmin(empCode);

	}

	@GetMapping("/fetchLocalities")
	public List<LocalityDto> fetchLocalities(@RequestParam int locCode) {
		return localityRepo.findByLocCode(locCode);
	}

	@GetMapping("/fetchColonies")
	public List<ColonyProjection> fetchColonies(@RequestParam Integer locality) {
		return colonyRepo.findAllColCodeAndColName(locality);
	}

	@GetMapping("/fetchStatuses")
	public List<GlobalVariableDto> fetchStatuses(@RequestParam String category) {

		List<Integer> excludedValues = Arrays.asList(ConstantDetails.ALTERNATIVE_PROVIDED,
				ConstantDetails.FLAT_COMBINED, ConstantDetails.FLAT_OCCUPIED);
		return globalrepo.findByCategoryAndCatValueNotIn(category, excludedValues);
	}

}
