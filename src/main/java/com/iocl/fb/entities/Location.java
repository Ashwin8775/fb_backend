package com.iocl.fb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FBNEW_LOCATION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {

	@Id
	@Column(name = "LOC_CODE")
	private int locCode;

	@Column(name = "LOC_NAME")
	private String locName;

	@Column(name = "FINAL_ALLOT_GRACE_DAYS_FLATS")
	private int finalAllotGraceDaysFlats;

	@Column(name = "ALLOT_OFFER_GRACE_DAYS_FLATS")
	private int allotOfferGraceDaysFlats;

	@Column(name = "FINAL_ALLOT_GRACE_DAYS_TRANSIT")
	private int finalAllotGraceDaysTransit;

	@Column(name = "ALLOT_OFFER_GRACE_DAYS_TRANSIT")
	private int allotOfferGraceDaysTransit;

	@Column(name = "MULTI_LOCALITY_FACILITY")
	private long multiLocalityFacility;

	@Column(name = "UPDATED_ON")
	private Date updatedOn;

	@Column(name = "UPDATED_BY")
	private int updatedBy;

	@Column(name = "FIN_ALLOT_GRC_DAYS_RENO_FLATS")
	private int finAllotGrcDaysRenoFlats;

	@Column(name = "ALLOT_OFR_GRC_DAYS_RENO_FLATS")
	private int allotOfrGrcDaysRenoFlats;

	@Column(name = "ALLOT_OFFER_NO_OF_DAYS")
	private long allotOfrNoOfDays;

	@Column(name = "ALLOT_OFFER_COUNT")
	private int allotOfrCount;

}
