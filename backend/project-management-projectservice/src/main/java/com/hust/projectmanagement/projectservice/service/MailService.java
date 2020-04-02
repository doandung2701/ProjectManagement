package com.hust.projectmanagement.projectservice.service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.hust.projectmanagement.projectservice.domain.Passcode;
import com.hust.projectmanagement.projectservice.domain.Project;
import com.hust.projectmanagement.projectservice.repository.PasscodeRepository;
import com.hust.projectmanagement.projectservice.utils.GenCodeUtils;
@Service
public class MailService {
	@Autowired
	  JavaMailSender mailSender; 
	  @Autowired
	  PasscodeRepository passcodeRepository;
	  
	  public String generatePasscodeForProgram(long projectId) {
	    String code = GenCodeUtils.OTP(8);
	    Passcode passcode = new Passcode();
	    passcode.setCode(code);
	    passcode.setProjectId(projectId);
	    passcodeRepository.save(passcode);
	    return code;
	  }

	  public String sendPasscode(String email, String passcode, Project prgm) {
	    String text = "You are invited to join program:\n"+prgm.getName()+"\n\nUse passcode given below:\n"+ passcode;
	    String subject = "Invitation to Join Program";
	    try {
	      sendMail(email, text, subject);
	    } catch (MessagingException e) {
	      return "";
	    }
	    return passcode;
	  }
	
	  public boolean sendMail(String to, String text, String subject, String res) throws MessagingException  {
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message,true);
	    try {
	      helper.setTo(to);
	      helper.setSubject(subject);
	      helper.setText(text);
	      ClassPathResource file = new ClassPathResource(res);
	      helper.addAttachment(res, file);
	    } catch (MessagingException e) {
	      e.printStackTrace();
	      return false;
	    }
	    mailSender.send(message);
	    return true;
	  }
	  
	  public boolean sendMail(String to, String text, String subject) throws MessagingException {
	    MimeMessage message = mailSender.createMimeMessage();
	    MimeMessageHelper helper = new MimeMessageHelper(message,true);
	    try {
	      helper.setTo(to);
	      helper.setSubject(subject);
	      helper.setText(text);
	    } catch (MessagingException e) {
	      e.printStackTrace();
	      return false;
	    }
	    mailSender.send(message);
	    return true;
	  }
}
