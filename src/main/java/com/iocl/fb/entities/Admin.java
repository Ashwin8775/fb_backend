package com.iocl.fb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "FBNEW_ADMIN")
@Entity
public class Admin {
	
	@Id
    @Column(name = "EMP_CODE", nullable = false)
    private Long empCode;

    @Column(name = "IOC_LOC_CODE", nullable = false)
    private Long iocLocCode;

    @Column(name = "LOC_CODE")
    private Integer locCode;

    @Column(name = "PSA_CODE", length = 20)
    private String psaCode;

    @Column(name = "CREATED_ON")
    private Date createdOn;

    @Column(name = "ROLE", length = 1)
    private String role;

    @Column(name = "EMAIL_ID", length = 100)
    private String emailId;

    @Column(name = "UPDATED_ON")
    private Date updatedOn;

    @Column(name = "UPDATED_BY")
    private Long updatedBy;
    
    @Column(name = "STATUS")
    private String status;
    

}
