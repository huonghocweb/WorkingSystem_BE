package com.huong.workingsystem.service;

import com.huong.workingsystem.model.dto.MailInfo;
import jakarta.mail.MessagingException;

public interface MailService {
    void send(MailInfo mailInfo ) throws MessagingException;
    void queue(MailInfo mailInfo);
}
