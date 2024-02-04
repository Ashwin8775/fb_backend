package com.iocl.fb.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iocl.fb.entities.EmployeeMaster;
import com.iocl.fb.payload.JwtAuthenticationResponse;
import com.iocl.fb.repository.EmployeeMasterDao;
import com.iocl.fb.repository.IocLocationRepo;
import com.iocl.fb.util.AESCrypt;

@Service
public class SetLogInTimeConstantsService {

	@Autowired
	private EmployeeMasterDao employeeMasterDao;

	@Autowired
	private IocLocationRepo locRepo;

	public JwtAuthenticationResponse storeLoginTimeConstants(JwtAuthenticationResponse authenticationResponse,
			String userId, String specialUser) {

		EmployeeMaster emp = employeeMasterDao.getEmployeeDet(Long.parseLong(userId));
		authenticationResponse.setUserId(specialUser != null ? userId : AESCrypt.encrypt(emp.getEmpCode().toString()));
		authenticationResponse.setEmpCode(String.valueOf(emp.getEmpCode()));
		authenticationResponse.setEmpName(emp.getEmpFname() + " " + emp.getEmpLname());
		authenticationResponse.setDesignation(emp.getDesignation());
		authenticationResponse.setLocCode(emp.getLocCode());
		authenticationResponse.setLocName(emp.getLocName());
		authenticationResponse.setMobileNo(emp.getMobileNumber());
		authenticationResponse.setEmailId(emp.getEmailId());
		authenticationResponse.setPsa(emp.getPsa());
		authenticationResponse.setPsaCode(emp.getPsaCode());
		authenticationResponse.setDivisionCode(emp.getDivisionCode());
		authenticationResponse.setDivisionName(emp.getDivisionName());
		authenticationResponse.setGrade(emp.getGrade());

		authenticationResponse.setLocId(locRepo.findLocCodeByIocLocCode(Integer.parseInt(emp.getLocCode())));

		// authenticationResponse.setUserRole(emp.getUserRole());

		authenticationResponse.setCompCode(emp.getCurrCompCode());
		authenticationResponse.setCompCodeDesc(emp.getCurrComp());

		authenticationResponse.setSessionId(123456789);

		return authenticationResponse;
	}
}
