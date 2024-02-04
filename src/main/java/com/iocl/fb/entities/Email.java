package com.iocl.fb.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name = "FBNEW_EMAIL")
public class Email {

	@Id
	@Column(name = "EMAIL_UID")
	@GeneratedValue(generator = "idSequence")
	@SequenceGenerator(schema = "FLATBKG", name = "idSequence", sequenceName = "FB_EMAIL_SEQ", allocationSize = 1)
	private Long emailUid;

	@Column(name = "REQUEST_ID")
	private Long requestId;

	@Column(name = "MAIL_FROM")
	private String mailFrom;

	@Column(name = "MAIL_TO")
	private String mailTo;

	@Column(name = "MAIL_CC")
	private String mailCc;

	@Column(name = "MAIL_BCC")
	private String mailBcc;

	@Column(name = "MAIL_SUBJECT")
	private String mailSubject;

	@Column(name = "MAIL_TEXT")
	private String mailText;

	@Column(name = "UPDATE_FLAG")
	private String updateFlag;

	@Column(name = "TRY_COUNT")
	private Integer tryCount;

	@Column(name = "SENT_ON")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date sentOn;

}
