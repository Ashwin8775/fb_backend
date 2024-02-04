package com.iocl.fb.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iocl.fb.constatnts.ConstantDetails;
import com.iocl.fb.dto.ExhibitProcResp;
import com.iocl.fb.dto.LocalityDto;
import com.iocl.fb.dto.VacantFlatdetailsDto;
import com.iocl.fb.entities.Email;
import com.iocl.fb.entities.EmailContent;
import com.iocl.fb.entities.Sms;
import com.iocl.fb.exception.EmailContentNotFoundException;
import com.iocl.fb.jobs.ExhibitServiceJob;
import com.iocl.fb.mailers.Mail;
import com.iocl.fb.mailers.MailResponse;
import com.iocl.fb.mailers.MailTo;
import com.iocl.fb.mailers.MailToCC;
import com.iocl.fb.mailers.Mobiles;
import com.iocl.fb.mailers.SendEmailSms;
import com.iocl.fb.mailers.SmsBody;
import com.iocl.fb.mailers.SmsBodyDb;
import com.iocl.fb.mailers.SmsResponse;
import com.iocl.fb.model.ExhibitLlocResp;
import com.iocl.fb.repository.EmailContentRepo;
import com.iocl.fb.repository.Emailrepo;
import com.iocl.fb.repository.FbHouseRepo;
import com.iocl.fb.repository.ProcedureCaller;
import com.iocl.fb.repository.SmsRepository;

/**
 * @author t_Salian
 *
 */
@Service
public class ExhibitFrontService {

	@Autowired
	ProcedureCaller procCall;

	@Autowired
	SendEmailSms mailSms;

	@Autowired
	Emailrepo emailRep;

	@Autowired
	SmsRepository smsRep;

	@Autowired
	ConstantDetails consts;

	@Autowired
	EmailContentRepo emailcontRepo;

	@Autowired
	FbHouseRepo houseRepo;
	
	@Autowired
	ExhibitServiceJob jobService;
	
	
	@Transactional
	public List<ExhibitLlocResp> exhibitLocalities(LocalityDto loc) {

		List<ExhibitLlocResp> locresp = new ArrayList<>();

		Long mailType = 21l;

		if (loc.getVacant() > 1) {
			mailType = 22l;
		}

		// Email content for INDICATING_PREFERENCES Email Type (21).
		Optional<EmailContent> findById = emailcontRepo.findByTypeAndUpdateFlag(mailType, "A");

		if (!findById.isPresent()) {
			throw new EmailContentNotFoundException("Email content with ID 21 not found");
		}
		EmailContent emaildBContent = findById.get();
		List<VacantFlatdetailsDto> vacantFlats = houseRepo.findVacantHousesDetails(loc.getLocalityCode());
		// Creating MailBody
		String bodyStructure = emaildBContent.getBody();
		String body = bodyStructure.replace("{Locality}", loc.getLocalityName());
		String mailBody = createMailBody(body, vacantFlats);

		// Mail Variables

		List<Mobiles> mobileNoList = new ArrayList<>();
		Map<String, Object> inputParams = new HashMap<>();
		inputParams = new HashMap<>();
		inputParams.put("I_LOC_CODE", 1);
		inputParams.put("I_LOCALITY", loc.getLocalityCode());
		inputParams.put("I_VACANT_FLATS", loc.getVacant());
		inputParams.put("I_FLAG_CHECK", 1);
		List<ExhibitProcResp> listOfUsers = procCall.callStoredProcedure("PROC_DUMMY", inputParams,
				ExhibitProcResp.class);

		for (ExhibitProcResp u : listOfUsers) {

			ExhibitLlocResp exhibitLlocResp = new ExhibitLlocResp();
			// Create Mail Object
			Mail mail = new Mail();
			List<MailTo> toList = new ArrayList<>();
			List<MailToCC> ccList = new ArrayList<>();

			mail.setFrom(emaildBContent.getMailFrom());
			mail.setSubject(emaildBContent.getSubject() + u.getReqId());
			mail.setBody(mailBody);
			mail.setApplicationName(emaildBContent.getApplicationName());

			// To List
			toList.add(new MailTo(u.getEmail(), "EMP", u.getEmpCode()));

			mail.setTo(toList);
			mail.setCc(ccList);

			exhibitLlocResp.setEmailId(u.getEmail());
			exhibitLlocResp.setEmpCode(u.getEmpCode());
			exhibitLlocResp.setMobileNumber(u.getMobileNo());
			exhibitLlocResp.setReqId(u.getReqId());
			exhibitLlocResp.setEmpName(u.getEmpName());
			exhibitLlocResp.setLocality(loc.getLocalityName());

			// Save Email Data Before mail
			Long emailUid = saveMailDets(mail, u.getReqId());

			// Send Mail
			MailResponse sendMail = mailSms.sendMail(mail);

			if (sendMail.isStatus()) {
				emailRep.updateStatus("S", emailUid);
				exhibitLlocResp.setMailStatus("Success");
				exhibitLlocResp.setMailMsg(sendMail.getMessage());

			} else {
				emailRep.updateStatus("F", emailUid);
				exhibitLlocResp.setMailStatus("Failure");
				exhibitLlocResp.setMailMsg(sendMail.getMessage());

			}

			// CC List
			// ccList.add(new MailToCC(u.getEmail(), "EMP", u.getEmpCode()));

			mail.setBcc(null);

			// Create Sms Object
			mobileNoList.add(new Mobiles(u.getMobileNo(), u.getReqId()));

			locresp.add(exhibitLlocResp);

		}

		/* No Send Sms To Respective Users */

		// Save Sms Data to Db
		SmsBodyDb smsContent = mailSms.createSmsContent(mobileNoList);

		List<Long> savedSmsDets = saveSmsDets(smsContent.getSmsObj());

		System.out.println("Headers = " + smsContent.getHeaders());

		// Send Sms
		SmsResponse sendSms = mailSms.sendSms(smsContent);

		if (sendSms.getStatus()) {

			smsRep.updateStatus("S", savedSmsDets);
			locresp.forEach(exhibitLlocResp -> {
				exhibitLlocResp.setSmsStatus("Success");
				exhibitLlocResp.setSmsMsg(sendSms.getMessage());
			});
		} else {
			smsRep.updateStatus("F", savedSmsDets);
			locresp.forEach(exhibitLlocResp -> {
				exhibitLlocResp.setSmsStatus("Failure");
				exhibitLlocResp.setSmsMsg(sendSms.getMessage());
			});
		}

		return locresp;

	}

	public Long saveMailDets(Mail mail, Long requetsId) {
		System.out.println(mail);
		List<String> toMailIds = mail.getTo().stream().map(MailTo::getRecipientMailId).collect(Collectors.toList());
		List<String> ccMailIds = mail.getCc().stream().map(MailToCC::getRecipientMailId).collect(Collectors.toList());

		Email email = new Email();
		email.setMailFrom(mail.getFrom());
		email.setMailSubject(mail.getSubject());
		email.setMailTo(createCommaSeparatedString(toMailIds));
		email.setMailCc(createCommaSeparatedString(ccMailIds));
		email.setMailText(mail.getBody());
		email.setRequestId(requetsId);
		email.setMailBcc(mail.getBcc());
		email.setTryCount(0);
		email.setUpdateFlag("P");

		System.out.println(email);

		Email savedEmail = emailRep.save(email);
		if (savedEmail != null) {
			return savedEmail.getEmailUid();
		}

		return 0l;

	}

	public List<Long> saveSmsDets(List<SmsBody> smsList) {
		List<Long> smsUidList = new ArrayList<>();
		// Map<Long, Long> mapData = new HashMap<>();

		for (SmsBody s : smsList) {
			Sms smsEn = new Sms();
			smsEn.setMobNo(Long.parseLong(s.getMobile_no()));
			smsEn.setRequestId(s.getReqId());
			smsEn.setSmsContent(s.getSms_content());
			smsEn.setSmsType(s.getTemplate_id());
			smsEn.setTryCount(0);
			smsEn.setUpdateFlag("P");
			smsEn.setSmsType("ENG");
			Sms saveSms = smsRep.save(smsEn);
			smsUidList.add(saveSms.getSmsUid());
			// mapData.put(saveSms.getRequestId(), saveSms.getSmsUid());
		}

		return smsUidList;

	}

	public static String createCommaSeparatedString(List<String> stringList) {
		if (stringList == null || stringList.isEmpty()) {
			return ""; // Return an empty string for an empty list
		} else if (stringList.size() == 1) {
			return stringList.get(0); // Return the single item as is
		} else {
			// Use StringJoiner to concatenate elements with commas
			StringJoiner joiner = new StringJoiner(", ");
			for (String item : stringList) {
				joiner.add(item);
			}
			return joiner.toString();
		}
	}

	public String createMailBody(String bodyData, List<VacantFlatdetailsDto> houseData) {
		StringBuffer htmlContent = new StringBuffer();

		htmlContent.append("<html>");
		htmlContent.append("<body>");
		htmlContent.append("<br>");
		htmlContent.append("<p>" + bodyData + "</p");
		htmlContent.append("<br>");
		htmlContent.append("<table border='1'>");
		htmlContent.append("<tr><th>HOUSE NO</th><th>LOCALITY</th><th>ADDRESS</th><th>Size</th></tr>");
		// Populate the table rows with data
		for (VacantFlatdetailsDto row : houseData) {
			htmlContent.append("<tr>");
			htmlContent.append("<td>").append(row.getHouseNumber()).append("</td>");
			htmlContent.append("<td>").append(row.getLocalityName()).append("</td>");
			htmlContent.append("<td>").append(row.getAddress1()).append("</td>");
			htmlContent.append("<td>").append(row.getResidSName()).append("</td>");
			htmlContent.append("</tr>");
		}
		htmlContent.append("</table>");
//		htmlContent.append("<style>");
//		htmlContent.append("body { font-family: Arial, sans-serif; }");
//		htmlContent.append("table { width: 50%; border-collapse: collapse;margin-top: 20px; }");
//		htmlContent.append("th, td { border: 1px solid #ddd;padding: 5px; text-align: left; }");
//		htmlContent.append("th { background-color: #f2f2f2; }");
//		htmlContent.append("p { margin-top: 10px; }");
//		htmlContent.append("</style>");
		htmlContent.append("</body>");
		htmlContent.append("</html>");

		return htmlContent.toString();
	}
	
	
	
}
