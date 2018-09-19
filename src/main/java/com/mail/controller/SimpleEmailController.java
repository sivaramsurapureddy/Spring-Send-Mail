package com.mail.controller;



import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class SimpleEmailController {
    
	@Autowired
    private JavaMailSender sender;
	
    @RequestMapping(value="/simpleemail",method = RequestMethod.POST)
    @ResponseBody
    String home(
    		@RequestBody MailBody mailBody,
    		@RequestParam("toMailId") String toMailId,
    		@RequestParam("subject") String subject) 
    {
        try 
        {
            sendEmail(mailBody,toMailId,subject);
            return "Email Sent!";
        }
        catch(Exception ex) 
        {
            return "Error in sending email: "+ex;
        }
    }
    
    
    public void sendEmail(MailBody mailBody,String toMailId,String subject) throws Exception{
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        
        
        String[] recipientList = toMailId.split(";");
        helper.setTo(recipientList);
        helper.setText(mailBody.getMailBody());
        helper.setSubject(subject);
        
        sender.send(message);
    }
}