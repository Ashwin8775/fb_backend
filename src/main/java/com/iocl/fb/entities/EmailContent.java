package com.iocl.fb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "FBNEW_EMAILCONTENT")
@IdClass(EmailContentId.class)
public class EmailContent {

	@Id
	@Column(name = "TYPE")
	private Long type;

	@Column(name = "TYPE_DESC")
	private String typeDesc;

	@Column(name = "MAIL_FROM")
	private String mailFrom;

	@Column(name = "SUBJECT")
	private String subject;

	@Column(name = "BODY")
	@Lob
	private String body;

	@Column(name = "APPLICATION_NAME")
	private String applicationName;

	@Id
	@Column(name = "UPDATE_FLAG")
	private String updateFlag;

	@Column(name = "UPDATED_BY")
	private String updatedBy;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	@Column(name = "UPDATED_ON")
	private Date updatedOn;

}