package com.iocl.fb.payload;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Id;

public class TblMstEsaDataPayload {
	
	
	private String forwardingId;
	
	private String applicationNo;
	
	private Date creationDate;

	private String generalQueryDesc;
	
	private String locationCode;
	
	private String auditLocationCode;
	
	private Date startDate;
	
	private Date endDate;
	
	private String natureWork;
	
	private String replyComments;

	private String recommendation;

	private String observation;

	public String getForwardingId() {
		return forwardingId;
	}

	public void setForwardingId(String forwardingId) {
		this.forwardingId = forwardingId;
	}

	public String getApplicationNo() {
		return applicationNo;
	}

	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public String getGeneralQueryDesc() {
		return generalQueryDesc;
	}

	public void setGeneralQueryDesc(String generalQueryDesc) {
		this.generalQueryDesc = generalQueryDesc;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	public String getAuditLocationCode() {
		return auditLocationCode;
	}

	public void setAuditLocationCode(String auditLocationCode) {
		this.auditLocationCode = auditLocationCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getNatureWork() {
		return natureWork;
	}

	public void setNatureWork(String natureWork) {
		this.natureWork = natureWork;
	}

	public String getReplyComments() {
		return replyComments;
	}

	public void setReplyComments(String replyComments) {
		this.replyComments = replyComments;
	}

	public String getRecommendation() {
		return recommendation;
	}

	public void setRecommendation(String recommendation) {
		this.recommendation = recommendation;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	@Override
	public String toString() {
		return "TblMstEsaDataPayload [forwardingId=" + forwardingId + ", applicationNo=" + applicationNo
				+ ", creationDate=" + creationDate + ", generalQueryDesc=" + generalQueryDesc + ", locationCode="
				+ locationCode + ", auditLocationCode=" + auditLocationCode + ", startDate=" + startDate + ", endDate="
				+ endDate + ", natureWork=" + natureWork + ", replyComments=" + replyComments + ", recommendation="
				+ recommendation + ", observation=" + observation + "]";
	}

	public TblMstEsaDataPayload() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TblMstEsaDataPayload(String forwardingId, String applicationNo, Date creationDate, String generalQueryDesc,
			String locationCode, String auditLocationCode, Date startDate, Date endDate, String natureWork,
			String replyComments, String recommendation, String observation) {
		super();
		this.forwardingId = forwardingId;
		this.applicationNo = applicationNo;
		this.creationDate = creationDate;
		this.generalQueryDesc = generalQueryDesc;
		this.locationCode = locationCode;
		this.auditLocationCode = auditLocationCode;
		this.startDate = startDate;
		this.endDate = endDate;
		this.natureWork = natureWork;
		this.replyComments = replyComments;
		this.recommendation = recommendation;
		this.observation = observation;
	}
	
	

}
