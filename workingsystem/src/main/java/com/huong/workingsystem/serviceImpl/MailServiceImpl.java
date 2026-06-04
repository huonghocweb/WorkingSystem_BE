package com.huong.workingsystem.serviceImpl;

import com.huong.workingsystem.model.dto.MailInfo;
import com.huong.workingsystem.service.MailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {
    private final JavaMailSender javaMailSender;

    @Override
    public void send(MailInfo mailInfo) throws MessagingException {
        MimeMessage mimeMessage =javaMailSender.createMimeMessage() ;
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true , "utf-8" );
        helper.setFrom(mailInfo.getFrom());
        helper.setTo(mailInfo.getTo());
        helper.setSubject(mailInfo.getSubject());
        helper.setText(mailInfo.getBody(),true);
        helper.setReplyTo(mailInfo.getFrom());
        String []cc =mailInfo.getCc()   ;
        if(cc != null  && cc.length> 0){
            helper.setCc(mailInfo.getCc());
        }
        String []  bcc = mailInfo.getBcc();
        if(bcc != null && bcc.length >0) {
            helper.setBcc(mailInfo.getBcc());
        }
        List<File> files =mailInfo.getFiles() ;
        if(files != null  ) {
            for(File file :  files) {
                helper.addAttachment(file.getName(),file);
            }
        }
        javaMailSender.send(mimeMessage);
    }

    @Override
    public void queue(MailInfo mailInfo) {

    }
}
