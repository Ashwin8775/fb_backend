package com.iocl.fb.constatnts;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * @author t_Salian
 *
 */
@SpringBootConfiguration
public class ConstantDetails {
	
	@Value("${mail.url}")
	public String MAIL_API_URL;
	
	@Value("${mail.username}")
	public String MAIL_API_USERNAME;
	
	@Value("${mail.password}")
	public String MAIL_API_PASSWORD;
	
	
	@Value("${mail.from}")
	public String MAIL_API_FROM;
	

	public static final int FLAT_REQ_PENDING = 50;
	public static final int FLAT_REQ_REJECTED_MAX_REJECTION = -50;
	public static final int FLAT_REQ_REJECTED_BY_ADMIN = -51;
	public static final int FLAT_REQ_APPROVED_BY_ADMIN = 51;
	public static final int FLAT_REQ_CANCELED = -52;
	public static final int FLAT_REQ_OFFERED = 52;
	public static final int FLAT_REQ_PREFERENCE_SELECTED = 53;
	public static final int FLAT_REQ_ALLOTMENT_REJECTED = -55;
	public static final int FLAT_REQ_ALLOTMENT_ACCEPTED = 55;
	public static final int FLAT_REQ_ALLOTTED = 54;
	public static final int FLAT_REQ_OCCUPIED = 56;
	public static final int FLAT_REQ_VACATED = 57;
	public static final int FLAT_REQ_VACATED_FINAL = 58;
	public static final int FLAT_REQ_AUTO_CANCEL = -58;
	public static final int FLAT_RENOVATED = 59;
	public static final int FLAT_NOT_RENOVATED = -59;

	public static final int FLAT_VACANT = 41;
	public static final int FLAT_ALLOTTED = 42;
	public static final int FLAT_ALLOTTED_MAINTENANCE_REDEVELOPMENT = 43;
	public static final int FLAT_OCCUPIED = 44;
	public static final int FLAT_HANDED_OVER = 45;
	public static final int FLAT_LONG_MAINTENANCE_REDEVELOPMENT = 46;
	public static final int FLAT_SOLD = 47;
	public static final int FLAT_RESERVED = -41;
	public static final int ALTERNATIVE_PROVIDED = -42;
	public static final int FLAT_COMBINED = -44;
	public static final String FLAT_STATUS_ALL = "41,42,43,44,45,46,47,-41";
	public static final String FLAT_STATUS_MANAGER_UPDATE = "41,42,46,47,-41";
	
	 public final static int ALLOTMENT_REVISED = -20, ALLOTMENT_PENDING_AT_USER = 20,
	          ALLOTMENT_REJECTED = -21, ALLOTMENT_ACCEPTED = 21;
	
	
	
	//NO OF RECORDS TO BE FETCHED
	public static final int NO_OF_RECORDS_EXHIBIT=3;

	public ConstantDetails() {

	}
	
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

}
