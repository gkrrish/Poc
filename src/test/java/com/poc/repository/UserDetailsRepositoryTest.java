package com.poc.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.poc.entity.UserDetails;

@SpringBootTest
public class UserDetailsRepositoryTest {

	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Test
	public void testFindByMobileNumber() {
		String mobileNumber = "+919876543210";

		UserDetails userDetails = userDetailsRepository.findByMobileNumber(mobileNumber).get();

		assertNotNull(userDetails);
		assertEquals(mobileNumber, userDetails.getMobileNumber());
	}

	@Test
    public void testGetUserDetailsByMobileNumber() {
        String mobileNumber = "+919876543210";

        List<Object[]> userDetailsList = userDetailsRepository.getUserDetailsByMobileNumber(mobileNumber);

        assertNotNull(userDetailsList);
        for (Object[] userDetails : userDetailsList) {
            assertNotNull(userDetails[0]); // mobileNumber
            assertNotNull(userDetails[1]); // newspaperName
            assertNotNull(userDetails[2]); // language
            assertNotNull(userDetails[3]); // state
            assertNotNull(userDetails[4]); // district
            assertNotNull(userDetails[5]); // mandal
            assertNotNull(userDetails[6]); // batchTime
            assertNotNull(userDetails[7]); // subscriptionCharges
            System.out.println(Arrays.toString(userDetails)); // Use Arrays.toString() to print array elements
        }
    }

}
