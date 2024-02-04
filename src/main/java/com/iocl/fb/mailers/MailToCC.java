package com.iocl.fb.mailers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MailToCC {

	String recipientType;
	String recipientMailId;
	String recipientCode;

}
