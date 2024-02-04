package com.iocl.fb.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "FBNEW_LOCALITY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Locality {
	
	
    @Column(name = "LOC_CODE")
    private int locCode;
    
    @Id
    @Column(name = "LOCALITY_CODE")
    private int localityCode;

    @Column(name = "LOCALITY_NAME")
    private String localityName;

    @Column(name = "LAST_ALLOTTED_CAT")
    private int lastAllottedCat;

    @Column(name = "TEMP_LAST_ALLOTTED")
    private int tempLastAllotted;

    @Column(name = "G_ABOVE_SEN_ALLOT")
    private int gAboveSenAllot;
    
    @Column(name = "RESID_SCODE")
    private Long residScode;
    
    @Column(name = "LAST_ALLOTED_COUNT")
    private int lastAllotedCount;

}
