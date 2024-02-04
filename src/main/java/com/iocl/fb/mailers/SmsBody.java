package com.iocl.fb.mailers;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SmsBody {

	private String mobile_no;
	private String sms_content;
	private String gst_flag;
	private String template_id;
	private String ref_in_msg_unique_id;

	@JsonIgnore
	private Long reqId;

}
