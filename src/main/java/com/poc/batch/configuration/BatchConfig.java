/*
 * package com.poc.batch.configuration;
 * 
 * import org.springframework.batch.core.Job; import
 * org.springframework.batch.core.Step; import
 * org.springframework.batch.core.configuration.annotation.
 * EnableBatchProcessing; import
 * org.springframework.batch.core.job.builder.JobBuilder; import
 * org.springframework.batch.core.repository.JobRepository; import
 * org.springframework.batch.core.step.builder.StepBuilder; import
 * org.springframework.batch.core.step.tasklet.Tasklet; import
 * org.springframework.context.annotation.Bean; import
 * org.springframework.context.annotation.Configuration; import
 * org.springframework.data.redis.core.RedisTemplate; import
 * org.springframework.mail.javamail.JavaMailSender; import
 * org.springframework.transaction.PlatformTransactionManager;
 * 
 * import com.poc.auser.main.repository.UserSubscriptionRepository; import
 * com.poc.batch.components.FetchUserSubscriptionsTasklet; import
 * com.poc.batch.components.SendEmailsTasklet;
 * 
 * @Configuration
 * 
 * @EnableBatchProcessing public class BatchConfig {
 * 
 * @Bean Job processNewspapersJob(JobRepository jobRepository, Step
 * fetchUserSubscriptionsStep, Step sendEmailsStep) { return new
 * JobBuilder("processNewspapersJob", jobRepository)
 * .start(fetchUserSubscriptionsStep) .next(sendEmailsStep) .build(); }
 * 
 * @Bean Step fetchUserSubscriptionsStep(JobRepository jobRepository,
 * PlatformTransactionManager transactionManager, Tasklet
 * fetchUserSubscriptionsTasklet) { return new
 * StepBuilder("fetchUserSubscriptionsStep", jobRepository)
 * .tasklet(fetchUserSubscriptionsTasklet, transactionManager) .build(); }
 * 
 * @Bean Step sendEmailsStep(JobRepository jobRepository,
 * PlatformTransactionManager transactionManager, Tasklet sendEmailsTasklet) {
 * return new StepBuilder("sendEmailsStep", jobRepository)
 * .tasklet(sendEmailsTasklet, transactionManager) .build(); }
 * 
 * @Bean Tasklet fetchUserSubscriptionsTasklet(UserSubscriptionRepository
 * userSubscriptionRepository, RedisTemplate<String, Object> redisTemplate) {
 * return new FetchUserSubscriptionsTasklet(userSubscriptionRepository,
 * redisTemplate); }
 * 
 * @Bean Tasklet sendEmailsTasklet(JavaMailSender mailSender,
 * RedisTemplate<String, Object> redisTemplate) { return new
 * SendEmailsTasklet(mailSender, redisTemplate); } }
 */