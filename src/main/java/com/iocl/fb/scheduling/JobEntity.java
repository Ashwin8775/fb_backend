package com.iocl.fb.scheduling;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "FBNEW_JOB_STATUS")
public class JobEntity {

	@Id
	@Column(name = "JOB_ID")
	private Integer jobId;

	@Column(name = "JOB_NAME")
	private String jobName;

	@Column(name = "METHOD_NAME")
	private String methodName;

	@Column(name = "STATUS")
	private Boolean status;

	@Column(name = "CRON_TIME")
	private String cronTime;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Column(name = "UPDATED_TIME")
	private Timestamp updatedTime;

}
