package com.iocl.fb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor 
@NoArgsConstructor
public class HouseDetailsDTO {

    private Long houseId;
    private String houseNo;
    private Long residTCode;
    private String residDesc;
    private Long residSCode;
    private String residSName;
    private Double areaSqft;
    private String buildingName;
    private String colName;
    private String localityName;
    private String locName;
    private Integer status;
    private Integer locCode;
}
