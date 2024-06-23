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
 * org.springframework.stereotype.Component;
 * 
 * import com.poc.auser.main.entity.UserSubscription; import
 * com.poc.auser.main.repository.UserSubscriptionRepository;
 * 
 * @Component public class FetchUserSubscriptionsTasklet implements Tasklet {
 * 
 * private final UserSubscriptionRepository userSubscriptionRepository; private
 * final RedisTemplate<String, Object> redisTemplate;
 * 
 * public FetchUserSubscriptionsTasklet(UserSubscriptionRepository
 * userSubscriptionRepository, RedisTemplate<String, Object> redisTemplate) {
 * this.userSubscriptionRepository = userSubscriptionRepository;
 * this.redisTemplate = redisTemplate; }
 * 
 * @Override public RepeatStatus execute(StepContribution contribution,
 * ChunkContext chunkContext) throws Exception { List<UserSubscription>
 * subscriptions = userSubscriptionRepository.findAll(); // Adjust this query as
 * per your need String batchId = (String)
 * chunkContext.getStepContext().getJobParameters().get("batchId");
 * 
 * redisTemplate.opsForValue().set("userSubscriptions:" + batchId,
 * subscriptions);
 * 
 * return RepeatStatus.FINISHED; } }
 */