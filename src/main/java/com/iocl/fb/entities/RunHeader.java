package com.iocl.fb.entities;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FBNEW_RUN_HEADER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RunHeader {

	@Id
	@Column(name = "RUN_ID")
	@GeneratedValue(generator = "idSequence")
	@SequenceGenerator(schema = "FLATBKG", name = "idSequence", sequenceName = "FBNEW_RUN_HEAD_SEQ", allocationSize = 1)
	private Long runId;

	@Column(name = "LOC_CODE")
	private Integer locCode;

	@Column(name = "LOCALITY_CODE")
	private Integer localityCode;

	@Column(name = "VACANT_FLATS")
	private Integer vacantFlats;

	@Column(name = "RUN_DATE")
	private LocalDate runDate;

	@Column(name = "END_DATE")
	private LocalDate endDate;

	@Column(name = "ALLOTED_FLATS")
	private Integer allotedFlats;

	public RunHeader(Integer locCode, Integer localityCode, Integer vacantFlats, LocalDate runDate, LocalDate endDate,
			Integer allotedFlats) {
		super();
		this.locCode = locCode;
		this.localityCode = localityCode;
		this.vacantFlats = vacantFlats;
		this.runDate = runDate;
		this.endDate = endDate;
		this.allotedFlats = allotedFlats;
	}

}
