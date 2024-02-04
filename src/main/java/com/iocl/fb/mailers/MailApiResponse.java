package com.iocl.fb.mailers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailApiResponse {
	private Boolean success;
	private String message;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
	private String timestamp;

	public static MailApiResponse fromJsonString(String jsonString) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			return objectMapper.readValue(jsonString, MailApiResponse.class);
		} catch (Exception e) {
			e.printStackTrace(); // Handle the exception according to your needs
			return null;
		}
	}

}
