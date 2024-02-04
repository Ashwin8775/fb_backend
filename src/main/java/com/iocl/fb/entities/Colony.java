package com.iocl.fb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FBNEW_COLONY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Colony {

    @Id
    @Column(name = "COL_CODE")
    private Long colCode;

    @Column(name = "COL_NAME")
    private String colName;

    @Column(name = "CLUSTER_ID")
    private Integer clusterId;

    @Column(name = "LOCALITY_CODE")
    private Integer localityCode;

    @Column(name = "ADDRESS1")
    private String address1;

    @Column(name = "ADDRESS2")
    private String address2;

    @Column(name = "PIN_CODE")
    private Integer pinCode;

    @Column(name = "ADMIN_LOC")
    private Integer adminLoc;

    @Column(name = "ADMIN_OFFICER")
    private Long adminOfficer;

    @Column(name = "ADMIN_EMAIL")
    private String adminEmail;

    @Column(name = "CLUSTER_RENO")
    private Integer clusterReno;

}

