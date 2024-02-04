package com.iocl.fb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FBNEW_IOC_LOCATION")
public class IocLocation {
	
	
   
    
    @Id
    @Column(name = "IOC_LOC_CODE")
    private int iocLocCode;
    
    @Column(name = "LOC_CODE")
    private int locCode;

    @Column(name = "IOC_LOC_NAME")
    private String iocLocName;

    @Column(name = "REPORTING_LOC_CODE")
    private int reportingLocCode;
    
   

}
