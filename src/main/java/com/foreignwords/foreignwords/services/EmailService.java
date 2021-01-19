package com.foreignwords.foreignwords.services;

import java.io.IOException;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;

public class EmailService {

	public void sendEmail(String fromWho, String toWho, String finalMessage) throws IOException {
		Email from = new Email(fromWho);
	    String subject = "Sending with SendGrid is Fun";
	    Email to = new Email(toWho);
	    Content content = new Content("text/html", finalMessage);
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid("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");
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
	}
	
	public void sendResetTokenEmail(String fromWho, String toWho, String finalMessage) throws IOException {
		Email from = new Email(fromWho);
	    String subject = "Your Password Reset Request for LinGA";
	    Email to = new Email(toWho);
	    Content content = new Content("text/html", finalMessage);
	    Mail mail = new Mail(from, subject, to, content);

	    SendGrid sg = new SendGrid("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");
	    Request request = new Request();
	    try {
	      request.setMethod(Method.POST);
	      request.setEndpoint("mail/send");
	      request.setBody(mail.build());
	      Response response = sg.api(request);
	      System.out.println(response.getStatusCode());
	      System.out.println(response.getBody());
	      System.out.println(response.getHeaders());
	    } catch (IOException ex) {
	      throw ex;
	    }
	}
}
