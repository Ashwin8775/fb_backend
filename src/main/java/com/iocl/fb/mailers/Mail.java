package com.iocl.fb.mailers;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    String from;
    List<MailTo> to;
    String bcc;
    List<MailToCC> cc;
    String subject;
    String body;
    String applicationName;
    boolean bodyToBeSaved;



}
