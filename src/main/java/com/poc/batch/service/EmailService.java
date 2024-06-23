/*
 * package com.poc.batch.service;
 * 
 * import java.io.File;
 * 
 * import org.springframework.core.io.FileSystemResource; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.mail.javamail.MimeMessageHelper; import
 * org.springframework.stereotype.Service;
 * 
 * import jakarta.mail.MessagingException; import
 * jakarta.mail.internet.MimeMessage;
 * 
 * @Service public class EmailService {
 * 
 * private final JavaMailSender mailSender;
 * 
 * public EmailService(JavaMailSender mailSender) { this.mailSender =
 * mailSender; }
 * 
 * public void sendEmailWithAttachment(String to, String subject, String text,
 * String pathToAttachment) throws MessagingException { MimeMessage message =
 * mailSender.createMimeMessage();
 * 
 * MimeMessageHelper helper = new MimeMessageHelper(message, true);
 * 
 * helper.setFrom("your-email@example.com"); helper.setTo(to);
 * helper.setSubject(subject); helper.setText(text);
 * 
 * FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
 * helper.addAttachment("Newspaper", file);
 * 
 * mailSender.send(message); } }
 */