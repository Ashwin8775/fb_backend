package com.iocl.fb.login;

import org.springframework.stereotype.Service;

@Service
public class AccessCheckService {

	public boolean isAccessAllowed(String userId) {
		// check from role access master from db
		// ....
		return true;
	}

}
