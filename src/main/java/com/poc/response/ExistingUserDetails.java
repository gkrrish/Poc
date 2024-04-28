package com.poc.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExistingUserDetails {

	private String mobileNumber;
	private List<String> languages;
	private List<String> subscribedNewsPapers;
	private List<String> NewsPaperBatchTime; //change later this all
	
}
