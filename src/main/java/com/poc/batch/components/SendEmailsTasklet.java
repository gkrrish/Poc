package com.poc.batch.components;

import java.io.File;
import java.util.List;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import com.poc.auser.main.entity.UserSubscription;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Component
public class SendEmailsTasklet implements Tasklet {

    private final JavaMailSender mailSender;
    private final RedisTemplate<String, Object> redisTemplate;

    public SendEmailsTasklet(JavaMailSender mailSender, RedisTemplate<String, Object> redisTemplate) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }
    
	@Value("${spring.mail.username}")
	private String fromMail;

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {
        String batchId = (String) chunkContext.getStepContext().getJobParameters().get("batchId");
        List<UserSubscription> subscriptions = (List<UserSubscription>) redisTemplate.opsForValue().get("userSubscriptions:" + batchId);

        for (UserSubscription subscription : subscriptions) {
            sendEmail(subscription);
        }

        return RepeatStatus.FINISHED;
    }

    public void sendEmail(UserSubscription subscription) throws MessagingException {
		/*
		 * SimpleMailMessage message = new SimpleMailMessage();
		 * message.setTo("gkrrish.11@gmail.com");
		 * message.setSubject("Your Daily Newspaper");
		 * message.setText("Here is your daily newspaper."); mailSender.send(message);
		 */
        
        sendMessageWithAttachment("gkrrish.11@gmail.com", "Testing mail", "Hello", "D:\\sts\\Poc\\src\\test\\resources\\files\\Eenadu.pdf");
    }
    
    public void sendMessageWithAttachment(String to, String subject, String text, String pathToAttachment)
			throws MessagingException {

		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);

		helper.setFrom("gkrrish.11@gmail.com");
		helper.setTo(to);
		helper.setSubject(subject);
		helper.setText(text);

		FileSystemResource file = new FileSystemResource(new File(pathToAttachment));
		helper.addAttachment("Invoice", file);

		mailSender.send(message);
	}
}
