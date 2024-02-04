package com.iocl.fb.mailers;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.iocl.fb.constatnts.ConstantDetails;
import com.iocl.fb.entities.SmsContent;
import com.iocl.fb.repository.SmsContentRepo;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class SendEmailSms {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ConstantDetails consts;

	@Autowired
	SmsContentRepo smsRepo;

	public MailResponse sendMail(Mail mail) {
		System.out.println(mail);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		String auth = consts.MAIL_API_USERNAME + ":" + consts.MAIL_API_PASSWORD;
		byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
		String authHeader = "Basic " + new String(encodedAuth);
		headers.add("Authorization", authHeader);

		try {
			ResponseEntity<MailApiResponse> response = restTemplate.exchange(consts.MAIL_API_URL, HttpMethod.POST,
					new HttpEntity<>(mail, headers), MailApiResponse.class);

			if (response.getStatusCode().is2xxSuccessful()) {
				log.info("Mail sent successfully. Response: {}", response);
				MailApiResponse body = response.getBody();
				return new MailResponse(body.getSuccess(), body, "Mail Sent Successfully");
			} else if (response.getStatusCode().is4xxClientError() || response.getStatusCode().is5xxServerError()) {
				log.error("Failed to send mail. Response: {}", response);
				MailApiResponse body = response.getBody();
				return new MailResponse(body.getSuccess(), body, "Mail Sent Failed");
			}

		} catch (HttpClientErrorException e) {
			log.error("HTTP error while sending mail. Status: {}, Response: {}", e.getStatusCode(),
					e.getResponseBodyAsString());
			MailApiResponse mailApiResponse = null;
			if (e.getResponseBodyAsString().contains("message")) {
				mailApiResponse = MailApiResponse.fromJsonString(e.getResponseBodyAsString());
			}

			return new MailResponse(false, mailApiResponse,
					mailApiResponse != null ? mailApiResponse.getMessage() : null);

		} catch (Exception e) {
			log.error("An error occurred while sending mail", e);

			return new MailResponse(false, null, "Mail Transmit Failed");

		}

		return new MailResponse(false, null, "Mail Transmit Failed");

	}

	public SmsResponse sendSms(SmsBodyDb sms) {

		try {

			ResponseEntity<SmsApiResponse> response = restTemplate.exchange(sms.getSmsUrl(), HttpMethod.POST,
					new HttpEntity<>(sms.getSmsObj(), sms.getHeaders()), SmsApiResponse.class);

			if (response.getStatusCode().is2xxSuccessful()) {
				if (response.getBody().getResponseCode().equals("SUCCESS")) {
					SmsApiResponse body = response.getBody();

					return new SmsResponse(true, body, "Sms Sent Successfully");
				} else {
					SmsApiResponse body = response.getBody();
					return new SmsResponse(false, body, body.getResponseCode());
				}
			}

		} catch (Exception e) {

			return new SmsResponse(false, null, "Failed To Send Sms due to + " + e.getLocalizedMessage());
		}

		return new SmsResponse(false, null, "No Sms Content in Database");

	}

	public SmsBodyDb createSmsContent(List<Mobiles> mobileNoList) {
		List<SmsContent> smsContentList = smsRepo.findAll();

		String smsUrl = "";
		String smsContentValue = null;
		String gstFlagValue = null;
		String templateIdValue = null;
		String refInMsgUniqueIdValue = null;

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		List<SmsContent> headerList = smsContentList.stream().filter(s -> s.getType().equalsIgnoreCase("H"))
				.collect(Collectors.toList());

		headerList.forEach(s -> headers.add(s.getParamName(), s.getDefaultValue()));

		Optional<String> smsUrlOptional = smsContentList.stream().filter(s -> s.getType().equalsIgnoreCase("U"))
				.map(SmsContent::getDefaultValue).findFirst();

		// Set smsUrl if present
		smsUrl = smsUrlOptional.orElse("");

		List<SmsContent> bodyList = smsContentList.stream().filter(s -> s.getType().equalsIgnoreCase("B"))
				.collect(Collectors.toList());

		for (SmsContent s : bodyList) {
			switch (s.getParamName()) {
			case "smsContent":
				smsContentValue = s.getDefaultValue();
				break;
			case "gst_flag":
				gstFlagValue = s.getDefaultValue();
				break;
			case "template_id":
				templateIdValue = s.getDefaultValue();
				break;
			case "ref_in_msg_unique_id":
				refInMsgUniqueIdValue = s.getDefaultValue();
				break;
			}
		}

		List<SmsBody> smsObj = new ArrayList<>();

		for (Mobiles m : mobileNoList) {
			SmsBody smsBody = new SmsBody();
			smsBody.setMobile_no(m.getMobileNo());
			smsBody.setSms_content(smsContentValue.replace("XXXX", String.valueOf(m.getReqId())));
			smsBody.setGst_flag(gstFlagValue);
			smsBody.setTemplate_id(templateIdValue);
			smsBody.setRef_in_msg_unique_id(refInMsgUniqueIdValue);
			smsBody.setReqId(m.getReqId());
			smsObj.add(smsBody);
		}

		return new SmsBodyDb(smsUrl, headers, smsObj);
	}

}
