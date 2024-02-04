package com.iocl.fb.jobs;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.iocl.fb.constatnts.ConstantDetails;
import com.iocl.fb.dto.LocalityDto;
import com.iocl.fb.dto.VacantFlatdetailsDto;
import com.iocl.fb.entities.CategoryRatio;
import com.iocl.fb.entities.Email;
import com.iocl.fb.entities.EmailContent;
import com.iocl.fb.entities.Locality;
import com.iocl.fb.entities.Location;
import com.iocl.fb.entities.RunHeader;
import com.iocl.fb.entities.Sms;
import com.iocl.fb.exception.EmailContentNotFoundException;
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
import com.iocl.fb.repository.CategoryRatioRepo;
import com.iocl.fb.repository.DynamicWaitListRepo;
import com.iocl.fb.repository.EmailContentRepo;
import com.iocl.fb.repository.Emailrepo;
import com.iocl.fb.repository.FbHouseRepo;
import com.iocl.fb.repository.FbReqDetRepo;
import com.iocl.fb.repository.FbReqHdrRepository;
import com.iocl.fb.repository.LocalityRepo;
import com.iocl.fb.repository.LocationRepo;
import com.iocl.fb.repository.RunHeaderRepo;
import com.iocl.fb.repository.SmsRepository;
import com.iocl.fb.service.JobLogService;
import com.iocl.fb.views.DynamicWaitListView;

@Service
public class ExhibitServiceJob {

	@Autowired
	LocalityRepo localityRepo;

	@Autowired
	CategoryRatioRepo catRatioRepo;

	@Autowired
	DynamicWaitListRepo waitListRepo;

	@Autowired
	FbHouseRepo houseRepo;

	@Autowired
	RunHeaderRepo runHeadRepo;

	@Autowired
	EmailContentRepo emailcontRepo;

	@Autowired
	SendEmailSms mailSms;

	@Autowired
	Emailrepo emailRep;

	@Autowired
	SmsRepository smsRep;

	@Autowired
	LocationRepo locRepo;

	@Autowired
	FbReqHdrRepository reqHdrRepo;

	@Autowired
	FbReqDetRepo reqDetRepo;

	@Autowired
	JobLogService jobLogs;

	private static Long HEADER_LOG_ID = 0L;

	@Transactional
	public void exhibitJobSchedule() {
		HEADER_LOG_ID = jobLogs.saveJobHeader(1l, "P", null);

		jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Exhibit Job Started At " + LocalDateTime.now());

		int OFFER_COUNT = 0;
		long GRACE_PERIOD = 0l;

		// Fetching the Grace Period and Offer Count
		Optional<Location> locationOpt = locRepo.findById(1);

		if (locationOpt.isPresent()) {
			OFFER_COUNT = locationOpt.get().getAllotOfrCount();
			GRACE_PERIOD = locationOpt.get().getAllotOfrNoOfDays();
		} else {
			return;
		}

		List<LocalityDto> vacantLocalities = houseRepo.findVacantLocalities(1);

		jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO",
				"Fetched " + vacantLocalities.size() + " Localities where there are vacant flats");

		for (LocalityDto dto : vacantLocalities) {

			jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Processing " + dto.getLocalityName() + " Locality");
			boolean firstCall = true;
			int vacantFlats = dto.getVacant().intValue();

			Optional<RunHeader> runHeadOpt = runHeadRepo.findByLocCodeAndLocalityCodeAndEndDate(1,
					dto.getLocalityCode(), LocalDate.now());

			if (runHeadOpt.isPresent()) {

				RunHeader runHeader = runHeadOpt.get();
				if (runHeader.getVacantFlats() - runHeader.getAllotedFlats() != 0) {
					firstCall = false;
					jobLogs.saveJobDetails(HEADER_LOG_ID, "DEBUG", "Some Flats are Already Exhibited in Run Id : "
							+ runHeader.getRunId()
							+ ",So the Indication will be done to all Request Id's in waiting List w.r.t to Category");
				}

			}

			if (firstCall)
				jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Triggering Job First Time for " + dto.getLocalityName()
						+ "in last " + GRACE_PERIOD + " days.");
			else
				jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Triggering Job Second Time for " + dto.getLocalityName()
						+ "in last " + GRACE_PERIOD + " days.");

			List<DynamicWaitListView> exhibitJobService = fetchAllEmailToExhibit(dto.getLocalityCode(), vacantFlats,
					firstCall, OFFER_COUNT);
			jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Total of " + exhibitJobService.size()
					+ " peoples are indicated for " + vacantFlats + " flats in " + dto.getLocalityName() + " Locality");

			List<VacantFlatdetailsDto> vacantFlatsDets = houseRepo.findVacantHousesDetails(dto.getLocalityCode());

			// Sending All the Mails to Respective EmailId's
			doMailingAndSms(exhibitJobService, dto, vacantFlatsDets);

			// Save The Data into FBNEW_RUN_HEADER
			RunHeader saveRunDetails = saveRunDetails(dto, vacantFlatsDets, GRACE_PERIOD);

			if (saveRunDetails != null) {
				// UPDATE THE STATUS TO 52 AGAINST ALL REQUESTIDs.
				List<Long> requestIdList = exhibitJobService.stream().map(DynamicWaitListView::getRequestId)
						.collect(Collectors.toList());

				jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO",
						"Request Id's (" + createCommaSeparatedString(requestIdList) + ") are indicated for "
								+ vacantFlats + " flats in " + dto.getLocalityName() + " Locality");

				reqHdrRepo.updateStatusForIds(ConstantDetails.FLAT_REQ_OFFERED, requestIdList);

				// Update the preferences for the Same in DEtails Table

				String prefOrder = vacantFlatsDets.stream().map(VacantFlatdetailsDto::getHouseId).map(String::valueOf)
						.collect(Collectors.joining(","));

				jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "House Id's (" + prefOrder
						+ ") are indicated as vacant in " + dto.getLocalityName() + " Locality");

				reqDetRepo.updatePrefDetsForIds(ConstantDetails.FLAT_REQ_OFFERED, prefOrder, requestIdList,
						dto.getLocalityCode());

			}

		}

		jobLogs.saveJobDetails(HEADER_LOG_ID, "INFO", "Exhibit Job Ended At " + LocalDateTime.now());

		jobLogs.updateStatusHeader("S", HEADER_LOG_ID);

	}

	public RunHeader saveRunDetails(LocalityDto dto, List<VacantFlatdetailsDto> vacantFlatsDets, long GRACE_PERIOD) {
		RunHeader runHeader = new RunHeader(1, dto.getLocalityCode(), dto.getVacant().intValue(), LocalDate.now(),
				LocalDate.now().plusDays(GRACE_PERIOD), 0);

		RunHeader savedHead = runHeadRepo.save(runHeader);

		return savedHead;

	}

	public void doMailingAndSms(List<DynamicWaitListView> exhibitJobService, LocalityDto dto,
			List<VacantFlatdetailsDto> vacantFlatsDets) {
		Long mailType = 21l;

		if (dto.getVacant() > 1) {
			mailType = 22l;
		}

		// Email content for INDICATING_PREFERENCES Email Type (21).
		Optional<EmailContent> findById = emailcontRepo.findByTypeAndUpdateFlag(mailType, "A");

		if (!findById.isPresent()) {
			throw new EmailContentNotFoundException("Email content with ID 21 not found");
		}
		EmailContent emaildBContent = findById.get();

		// Creating MailBody
		String bodyStructure = emaildBContent.getBody();
		String body = bodyStructure.replace("{Locality}", dto.getLocalityName());
		String mailBody = createMailBody(body, vacantFlatsDets);

		// Mail Variables

		List<Mobiles> mobileNoList = new ArrayList<>();
		for (DynamicWaitListView u : exhibitJobService) {

			ExhibitLlocResp exhibitLlocResp = new ExhibitLlocResp();
			// Create Mail Object
			Mail mail = new Mail();
			List<MailTo> toList = new ArrayList<>();
			List<MailToCC> ccList = new ArrayList<>();

			mail.setFrom(emaildBContent.getMailFrom());
			mail.setSubject(emaildBContent.getSubject() + u.getRequestId());
			mail.setBody(mailBody);
			mail.setApplicationName(emaildBContent.getApplicationName());

			// To List
			toList.add(new MailTo(u.getEmailId(), "EMP", String.valueOf(u.getEmpCode())));

			mail.setTo(toList);
			mail.setCc(ccList);

			exhibitLlocResp.setEmailId(u.getEmailId());
			exhibitLlocResp.setEmpCode(String.valueOf(u.getEmpCode()));
			exhibitLlocResp.setMobileNumber(u.getMobileNo());
			exhibitLlocResp.setReqId(u.getRequestId());
			exhibitLlocResp.setEmpName(u.getEmpName());
			exhibitLlocResp.setLocality(dto.getLocalityName());

			// Save Email Data Before mail
			Long emailUid = saveMailDets(mail, u.getRequestId());

			mail.setBcc(null);

			// Send Mail
//			MailResponse sendMail = mailSms.sendMail(mail);
//			
//
//			if (sendMail.isStatus()) {
//				emailRep.updateStatus("S", emailUid);
//				exhibitLlocResp.setMailStatus("Success");
//				exhibitLlocResp.setMailMsg(sendMail.getMessage());
//
//			} else {
//				emailRep.updateStatus("F", emailUid);
//				exhibitLlocResp.setMailStatus("Failure");
//				exhibitLlocResp.setMailMsg(sendMail.getMessage());
//
//			}

			// CC List
			// ccList.add(new MailToCC(u.getEmail(), "EMP", u.getEmpCode()));

			// Create Sms Object
			mobileNoList.add(new Mobiles(u.getMobileNo(), u.getRequestId()));

		}

		// Save Sms Data to Db
		SmsBodyDb smsContent = mailSms.createSmsContent(mobileNoList);

		List<Long> savedSmsDets = saveSmsDets(smsContent.getSmsObj());

		// Send Sms
//		SmsResponse sendSms = mailSms.sendSms(smsContent);
//
//		if (sendSms.getStatus()) {
//			smsRep.updateStatus("S", savedSmsDets);
//		} else {
//			smsRep.updateStatus("F", savedSmsDets);
//		}

	}

	public Long saveMailDets(Mail mail, Long requetsId) {

		List<String> toMailIds = mail.getTo().stream().map(MailTo::getRecipientMailId).collect(Collectors.toList());
		List<String> ccMailIds = mail.getCc().stream().map(MailToCC::getRecipientMailId).collect(Collectors.toList());

		Email email = new Email();
		email.setMailFrom(mail.getFrom());
		email.setMailSubject(mail.getSubject());
		email.setMailTo(createCommaSeparatedString(toMailIds));
		email.setMailCc(createCommaSeparatedString(ccMailIds));
		email.setMailText(mail.getBody().toString());
		email.setRequestId(requetsId);
		email.setMailBcc(mail.getBcc());
		email.setTryCount(0);
		email.setUpdateFlag("P");

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

	public static String createCommaSeparatedString(List<?> list) {
		if (list == null || list.isEmpty()) {
			return ""; // Return an empty string for an empty list
		} else if (list.size() == 1) {
			return String.valueOf(list.get(0)); // Return the single item as is
		} else {
			// Use StringJoiner to concatenate elements with commas
			StringJoiner joiner = new StringJoiner(", ");
			for (Object item : list) {
				joiner.add(String.valueOf(item));
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
		htmlContent.append("</body>");
		htmlContent.append("</html>");

		return htmlContent.toString();
	}

	public List<DynamicWaitListView> fetchAllEmailToExhibit(Integer locality, Integer vacantFlats, boolean flagCheck,
			int OFFER_COUNT) {
		List<DynamicWaitListView> emailList = new ArrayList<>();
		Map<Integer, Integer> addedListCounts = new HashMap<>();

		Optional<Locality> locDetsOpt = localityRepo.findById(locality);

		if (locDetsOpt.isPresent()) {
			Locality locDets = locDetsOpt.get();
			int lastAllotedCat = locDets.getLastAllottedCat();
			int lastAllotedCount = locDets.getLastAllotedCount();

			Map<Integer, Integer> allocDetails = new HashMap<>();
			allocDetails.put(lastAllotedCat, lastAllotedCount);

			Map<Integer, Integer> catCodeToRatioMap = catRatioRepo.findByLocalityCodeAndStatus(locality, "A").stream()
					.collect(Collectors.toMap(CategoryRatio::getCatCode, CategoryRatio::getRatio));

			catCodeToRatioMap.keySet().forEach(catCode -> {
				allocDetails.putIfAbsent(catCode, 0);
				addedListCounts.putIfAbsent(catCode, 0);
			});

			for (int i = 0; i < vacantFlats; i++) {
				Integer ratio = catCodeToRatioMap.get(lastAllotedCat);

				if (allocDetails.get(lastAllotedCat) < ratio) {
					int rnk = addedListCounts.get(lastAllotedCat);
					processCategory(locDets, emailList, addedListCounts, allocDetails, lastAllotedCat, rnk, flagCheck,
							OFFER_COUNT);
				} else {
					allocDetails.put(lastAllotedCat, 0);
					lastAllotedCat = getNextCategoryCode(lastAllotedCat, catCodeToRatioMap.keySet());
					int rnk = addedListCounts.get(lastAllotedCat);
					processCategory(locDets, emailList, addedListCounts, allocDetails, lastAllotedCat, rnk, flagCheck,
							OFFER_COUNT);
				}
			}
		}

		// Remove duplicates while preserving order
		List<DynamicWaitListView> distinctEmailList = emailList.stream().distinct().collect(Collectors.toList());

		return distinctEmailList;
	}

	private void processCategory(Locality locDets, List<DynamicWaitListView> emailList,
			Map<Integer, Integer> addedListCounts, Map<Integer, Integer> allocDetails, int lastAllotedCat, int rnk,
			boolean flagCheck, int OFFER_COUNT) {

		if (flagCheck) {
			// Fetch top records from the current category
			List<DynamicWaitListView> fetchedList = waitListRepo
					.findByLocalityCodeAndAppCatAndRnkGreaterThanOrderByRnkAsc(locDets.getLocalityCode(),
							lastAllotedCat, rnk, PageRequest.of(0, OFFER_COUNT));

			int currentListSize = fetchedList.size();
			addedListCounts.put(lastAllotedCat, rnk + currentListSize);

			// Check if there are fewer than three values in the fetched list
			while (currentListSize < OFFER_COUNT) {
				// Fetch additional records from the next category
				int nextCatCode = getNextCategoryCode(lastAllotedCat, allocDetails.keySet());
				int nextRnk = addedListCounts.get(nextCatCode);
				List<DynamicWaitListView> nextCatList = waitListRepo
						.findByLocalityCodeAndAppCatAndRnkGreaterThanOrderByRnkAsc(locDets.getLocalityCode(),
								nextCatCode, nextRnk, PageRequest.of(0, OFFER_COUNT - currentListSize));

				if (nextCatList.isEmpty()) {
					break; // Break the loop if no more records are found
				}

				fetchedList.addAll(nextCatList);
				currentListSize = fetchedList.size();
			}

			// Trim the list to ensure it contains exactly three records
			if (currentListSize > OFFER_COUNT) {
				fetchedList = fetchedList.subList(0, OFFER_COUNT);
			}

			emailList.addAll(fetchedList);
			allocDetails.put(lastAllotedCat, allocDetails.get(lastAllotedCat) + 1);
		} else {
			List<DynamicWaitListView> fetchedList = waitListRepo
					.findByLocalityCodeAndAppCatOrderByRnkAsc(locDets.getLocalityCode(), lastAllotedCat);
			emailList.addAll(fetchedList);
			allocDetails.put(lastAllotedCat, allocDetails.get(lastAllotedCat) + 1);
		}
	}

	private int getNextCategoryCode(int currentCatCode, Set<Integer> categoryCodes) {
		List<Integer> sortedCodes = new ArrayList<>(categoryCodes);
		Collections.sort(sortedCodes);

		int index = sortedCodes.indexOf(currentCatCode);
		if (index == sortedCodes.size() - 1) {
			// If the current category is the last one, wrap around to the first category
			return sortedCodes.get(0);
		} else {
			// Otherwise, move to the next category
			return sortedCodes.get(index + 1);
		}
	}

}
