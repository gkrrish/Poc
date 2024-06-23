//package com.poc.batch.components;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.mock;
//import static org.mockito.Mockito.verify;
//import static org.mockito.Mockito.when;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.ArgumentCaptor;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.springframework.batch.core.StepContribution;
//import org.springframework.batch.core.scope.context.ChunkContext;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.ValueOperations;
//import org.springframework.mail.SimpleMailMessage;
//import org.springframework.mail.javamail.JavaMailSender;
//
//import com.poc.auser.main.entity.UserSubscription;
//
//class SendEmailsTaskletTest {
//
//    @Mock
//    private JavaMailSender mailSender;
//
//    @Mock
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Mock
//    private ValueOperations<String, Object> valueOperations;
//
//    @InjectMocks
//    private SendEmailsTasklet sendEmailsTasklet;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.openMocks(this);
//        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
//    }
//
//    @Test
//    void testExecute() throws Exception {
//        List<UserSubscription> subscriptions = new ArrayList<>();
//        UserSubscription subscription = new UserSubscription();
//        subscriptions.add(subscription);
//
//        when(valueOperations.get("userSubscriptions:1")).thenReturn(subscriptions);
//
//        StepContribution stepContribution = mock(StepContribution.class);
//        ChunkContext chunkContext = mock(ChunkContext.class);
//        when(chunkContext.getStepContext().getJobParameters().get("batchId")).thenReturn("1");
//
//        sendEmailsTasklet.execute(stepContribution, chunkContext);
//
//        ArgumentCaptor<SimpleMailMessage> messageCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
//        verify(mailSender).send(messageCaptor.capture());
//
//        SimpleMailMessage sentMessage = messageCaptor.getValue();
//        assertEquals("gkrrish.11@gmail.com", sentMessage.getTo()[0]);
//        assertEquals("Your Daily Newspaper", sentMessage.getSubject());
//        assertEquals("Here is your daily newspaper.", sentMessage.getText());
//    }
//}
