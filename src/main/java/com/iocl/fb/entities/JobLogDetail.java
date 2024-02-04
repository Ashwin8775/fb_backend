package com.iocl.fb.entities;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "FBNEW_JOB_LOG_DETAILS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobLogDetail {

	@Id
	@Column(name = "DETAIL_ID")
	@GeneratedValue(generator = "idSequence")
	@SequenceGenerator(schema = "FLATBKG", name = "idSequence", sequenceName = "ISEQ$$_22510598", allocationSize = 1)
	private Long detailId;

	@Column(name = "LOG_ID")
	private Long logId;

	@Column(name = "LOG_LEVEL")
	private String logLevel;

	@Column(name = "LOG_TIME")
	@CreationTimestamp
	private LocalDateTime logTime;

	@Lob
	@Column(name = "LOG_MESSAGE")
	private String logMessage;

}
