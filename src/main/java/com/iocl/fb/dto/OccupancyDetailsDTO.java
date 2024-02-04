package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OccupancyDetailsDTO {

	private Long empCode;
    private String empName;
    private String currGrade;
    private String desig;
    private String requestDate;
    private String appCat;
    private String allotmentDate;
    private String occupationDate;
    private Long requestId;
    private Long houseId;
    private String houseNo;
    private String buildingName;
    private Integer localityCode;
    private Integer locCode;
    private String emailId;

}
