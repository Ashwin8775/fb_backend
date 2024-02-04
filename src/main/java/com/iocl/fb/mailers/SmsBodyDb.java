package com.iocl.fb.mailers;

import java.util.List;

import org.springframework.http.HttpHeaders;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsBodyDb {

	String smsUrl;
	HttpHeaders headers;
	List<SmsBody> smsObj;

}
