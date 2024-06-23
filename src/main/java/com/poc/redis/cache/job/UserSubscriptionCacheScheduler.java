package com.poc.redis.cache.job;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.poc.auser.main.entity.UserSubscription;
import com.poc.auser.main.repository.UserSubscriptionRepository;
import com.poc.redis.cache.service.RedisCacheService;

@Service
public class UserSubscriptionCacheScheduler {

    @Autowired
    private RedisCacheService redisCacheService;

    @Autowired
    private UserSubscriptionRepository userSubscriptionRepository;

    @Scheduled(cron = "0 */2 * * * *") // runs every 2 minutes
    public void cacheUserSubscriptions() {
        List<UserSubscription> subscriptions = userSubscriptionRepository.findAll();
        for (UserSubscription subscription : subscriptions) {
            String key = "user:subscription:" + subscription.getId().getUser().getUserid();
            redisCacheService.cacheUserSubscription(key, subscription);
        }
    }
}
