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
@Table(name = "FBNEW_SMS")
public class Sms {

	@Id
	@Column(name = "SMS_UID")
	@GeneratedValue(generator = "idSequence")
	@SequenceGenerator(schema = "FLATBKG", name = "idSequence", sequenceName = "FB_SMS_SEQ", allocationSize = 1)
	private Long smsUid;

	@Column(name = "REQUEST_ID")
	private Long requestId;

	@Column(name = "MOB_NO")
	private Long mobNo;

	@Column(name = "SMS_TYPE")
	private String smsType;

	@Column(name = "SMS_CONTENT")
	private String smsContent;

	@Column(name = "UPDATE_FLAG")
	private String updateFlag;

	@Column(name = "SENT_DT")
	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date sentDt;

	@Column(name = "TRY_COUNT")
	private Integer tryCount;

}
