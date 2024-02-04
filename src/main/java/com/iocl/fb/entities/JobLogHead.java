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
@Table(name = "FBNEW_JOB_LOG")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobLogHead {

	@Id
	@Column(name = "LOG_ID")
	@GeneratedValue(generator = "idSequence")
	@SequenceGenerator(schema = "FLATBKG", name = "idSequence", sequenceName = "ISEQ$$_22510589", allocationSize = 1)
	private Long logId;

	@Column(name = "EXECUTION_TIME")
	@CreationTimestamp
	private LocalDateTime executionTime;

	@Column(name = "JOB_ID")
	private Long jobId;

	@Column(name = "STATUS")
	private String status;

	@Lob
	@Column(name = "ERROR_MESSAGE")
	private String errorMessage;

	@Column(name = "JOB_DATE")
	private String jobDate;

}
