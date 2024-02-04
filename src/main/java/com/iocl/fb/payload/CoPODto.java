package com.iocl.fb.payload;

import java.util.Date;

public class CoPODto {
	private String id;
	private String itemDesc;
	private String tenderType;
	private String compCode;
	private String poNo;
	private Date dateOfPo;
	private String partyName;
	private double amount;
	private String approvingAuth;
	private String justificationST;
	private String reasonabilityPrice;
	private String natureOfWork;
	public CoPODto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public CoPODto(String id, String itemDesc, String tenderType, String compCode, String poNo, Date dateOfPo,
			String partyName, double amount, String approvingAuth, String justificationST, String reasonabilityPrice,
			String natureOfWork) {
		super();
		this.id = id;
		this.itemDesc = itemDesc;
		this.tenderType = tenderType;
		this.compCode = compCode;
		this.poNo = poNo;
		this.dateOfPo = dateOfPo;
		this.partyName = partyName;
		this.amount = amount;
		this.approvingAuth = approvingAuth;
		this.justificationST = justificationST;
		this.reasonabilityPrice = reasonabilityPrice;
		this.natureOfWork = natureOfWork;
	}
	@Override
	public String toString() {
		return "CoPODto [id=" + id + ", itemDesc=" + itemDesc + ", tenderType=" + tenderType + ", compCode=" + compCode
				+ ", poNo=" + poNo + ", dateOfPo=" + dateOfPo + ", partyName=" + partyName + ", amount=" + amount
				+ ", approvingAuth=" + approvingAuth + ", justificationST=" + justificationST + ", reasonabilityPrice="
				+ reasonabilityPrice + ", natureOfWork=" + natureOfWork + "]";
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getItemDesc() {
		return itemDesc;
	}
	public void setItemDesc(String itemDesc) {
		this.itemDesc = itemDesc;
	}
	public String getTenderType() {
		return tenderType;
	}
	public void setTenderType(String tenderType) {
		this.tenderType = tenderType;
	}
	public String getCompCode() {
		return compCode;
	}
	public void setCompCode(String compCode) {
		this.compCode = compCode;
	}
	public String getPoNo() {
		return poNo;
	}
	public void setPoNo(String poNo) {
		this.poNo = poNo;
	}
	public Date getDateOfPo() {
		return dateOfPo;
	}
	public void setDateOfPo(Date dateOfPo) {
		this.dateOfPo = dateOfPo;
	}
	public String getPartyName() {
		return partyName;
	}
	public void setPartyName(String partyName) {
		this.partyName = partyName;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getApprovingAuth() {
		return approvingAuth;
	}
	public void setApprovingAuth(String approvingAuth) {
		this.approvingAuth = approvingAuth;
	}
	public String getJustificationST() {
		return justificationST;
	}
	public void setJustificationST(String justificationST) {
		this.justificationST = justificationST;
	}
	public String getReasonabilityPrice() {
		return reasonabilityPrice;
	}
	public void setReasonabilityPrice(String reasonabilityPrice) {
		this.reasonabilityPrice = reasonabilityPrice;
	}
	public String getNatureOfWork() {
		return natureOfWork;
	}
	public void setNatureOfWork(String natureOfWork) {
		this.natureOfWork = natureOfWork;
	}
	
	
}
