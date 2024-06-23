package com.poc.batch.writer;

import java.util.List;

import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.poc.auser.main.entity.UserSubscription;

@Component
public class UserSubscriptionItemWriter implements ItemWriter<UserSubscription> {

    public void write(List<? extends UserSubscription> items) {
        for (UserSubscription item : items) {
            System.out.println(item);
        }
    }

	@Override
	public void write(Chunk<? extends UserSubscription> chunk) throws Exception {
		
	}
}
