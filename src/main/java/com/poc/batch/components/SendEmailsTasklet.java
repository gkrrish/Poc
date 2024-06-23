/*
 * package com.poc.batch.components;
 * 
 * import java.util.List;
 * 
 * import org.springframework.batch.core.StepContribution; import
 * org.springframework.batch.core.scope.context.ChunkContext; import
 * org.springframework.batch.core.step.tasklet.Tasklet; import
 * org.springframework.batch.repeat.RepeatStatus; import
 * org.springframework.data.redis.core.RedisTemplate; import
 * org.springframework.mail.SimpleMailMessage; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.stereotype.Component;
 * 
 * import com.poc.auser.main.entity.UserSubscription;
 * 
 * import jakarta.mail.MessagingException;
 * 
 * @Component public class SendEmailsTasklet implements Tasklet {
 * 
 * private final JavaMailSender mailSender; private final RedisTemplate<String,
 * Object> redisTemplate;
 * 
 * public SendEmailsTasklet(JavaMailSender mailSender, RedisTemplate<String,
 * Object> redisTemplate) { this.mailSender = mailSender; this.redisTemplate =
 * redisTemplate; }
 * 
 * @Override public RepeatStatus execute(StepContribution contribution,
 * ChunkContext chunkContext) throws Exception { String batchId = (String)
 * chunkContext.getStepContext().getJobParameters().get("batchId");
 * List<UserSubscription> subscriptions = (List<UserSubscription>)
 * redisTemplate.opsForValue().get("userSubscriptions:" + batchId);
 * 
 * for (UserSubscription subscription : subscriptions) {
 * sendEmail(subscription); }
 * 
 * return RepeatStatus.FINISHED; }
 * 
 * public void sendEmail(UserSubscription subscription) throws
 * MessagingException { SimpleMailMessage message = new SimpleMailMessage();
 * message.setTo("gkrrish.11@gmail.com");
 * message.setSubject("Your Daily Newspaper");
 * message.setText("Here is your daily newspaper."); mailSender.send(message); }
 * }
 */