package com.iocl.fb.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApprovaldetailsDto {

	private Long requestId;
	private String empName;
	private String category;
	private String division;
	private int currIocLocCode;
	private String grade;
	private String desig;
	private int localityCode;
	private String locality;
	private Long contact1;
	private String familyRetn;
	private String ownAcco;
	private String remarks;
	private String remarksAdmin;
	private String prefOrder;
	private Integer itemStatus;
	private boolean showTableStatus;
	private boolean showPreferenceStatus;
	private boolean showUpdateStatus;

	private List<HouseListDto> houseList;

	public ApprovaldetailsDto(Long requestId, String empName, String category, String division, int currIocLocCode,
			String grade, String desig, int localityCode, String locality, Long contact1, String familyRetn,
			String ownAcco, String remarks, String remarksAdmin, String prefOrder, Integer itemStatus) {
		super();
		this.requestId = requestId;
		this.empName = empName;
		this.category = category;
		this.division = division;
		this.currIocLocCode = currIocLocCode;
		this.grade = grade;
		this.desig = desig;
		this.localityCode = localityCode;
		this.locality = locality;
		this.contact1 = contact1;
		this.familyRetn = familyRetn;
		this.ownAcco = ownAcco;
		this.remarks = remarks;
		this.remarksAdmin = remarksAdmin;
		this.prefOrder = prefOrder;
		this.itemStatus = itemStatus;
	}

	
}
