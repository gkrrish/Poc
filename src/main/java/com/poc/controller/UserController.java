package com.poc.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.poc.entity.UserDetails;
import com.poc.pdfservice.PdfService;
import com.poc.request.WelcomeRequest;
import com.poc.response.ExistingUserDetails;
import com.poc.response.WelcomeResponse;
import com.poc.service.UserService;
import com.poc.util.StringUtils;

@RestController
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private PdfService pdfService;
	@Autowired
    private TemplateEngine templateEngine;

	@PostMapping("/welcome") // fake charges?
	@ResponseStatus
	public ResponseEntity<?> welcomeUser(@RequestBody WelcomeRequest request) {
		UserDetails userDetails = userService.notExistingUser(request.getMobileNumber());

		if (userDetails == null) {
			WelcomeResponse welcomeResponse = new WelcomeResponse(StringUtils.WELCOME_MESSAGE);
			List<String> languages = userService.getAllLanguges();
			welcomeResponse.setLanguages(languages);

			return ResponseEntity.ok(welcomeResponse);

		} else {
			ExistingUserDetails existingUserDetails = userService.getSubscriptioinDetails(userDetails.getMobileNumber());
			/*byte[] invoice = null;
			try {
				invoice = pdfService.generateInvoice(existingUserDetails);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "invoice.pdf"); 

            return new ResponseEntity<>(invoice, headers, HttpStatus.OK);
            
            */
			Context context = new Context();
	        context.setVariable("existingUserDetails", existingUserDetails);
	        String htmlContent = templateEngine.process("invoice-template", context);
	        HttpHeaders headers = new HttpHeaders();
	        headers.add("Content-Type", "text/html; charset=UTF-8");
	        return new ResponseEntity<>(htmlContent, headers, HttpStatus.OK);

		}
	}

	@GetMapping("/states/{mobileNumber}")
	@ResponseStatus
	@ResponseBody
	public List<String> getStates(@PathVariable String mobileNumber) {
		return userService.getAllStates();
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDetails> getUserDetails(@PathVariable Long userId) {
		Optional<UserDetails> userDetailsOptional = userService.getUserDetails(userId);
		if (userDetailsOptional.isPresent()) {
			return ResponseEntity.ok(userDetailsOptional.get());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
