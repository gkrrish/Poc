//package com.poc.batch.components;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.batch.core.JobExecution;
//import org.springframework.batch.core.JobParametersBuilder;
//import org.springframework.batch.test.JobLauncherTestUtils;
//import org.springframework.batch.test.context.SpringBatchTest;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//
//import com.poc.auser.main.repository.UserSubscriptionRepository;
//
//@SpringBatchTest
//@SpringBootTest
//@SpringJUnitConfig(classes = {BatchJobTest.TestConfig.class})
//public class BatchJobTest {
//
//    @Autowired
//    private JobLauncherTestUtils jobLauncherTestUtils;
//
//    @Mock
//    private UserSubscriptionRepository userSubscriptionRepository;
//
//    @Mock
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @InjectMocks
//    private FetchUserSubscriptionsTasklet fetchUserSubscriptionsTasklet;
//
//    @InjectMocks
//    private SendEmailsTasklet sendEmailsTasklet;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testJob() throws Exception {
//        when(userSubscriptionRepository.findAll()).thenReturn(new ArrayList<>());
//
//        JobExecution jobExecution = jobLauncherTestUtils.launchJob(new JobParametersBuilder()
//                .addString("batchId", "1")
//                .toJobParameters());
//
//        assertEquals("COMPLETED", jobExecution.getExitStatus().getExitCode());
//    }
//
//    @Configuration
//    static class TestConfig {
//
//        @Bean
//        public JobLauncherTestUtils jobLauncherTestUtils() {
//            return new JobLauncherTestUtils();
//        }
//
//        // Define other necessary beans that are not defined in BatchConfig
//    }
//}
