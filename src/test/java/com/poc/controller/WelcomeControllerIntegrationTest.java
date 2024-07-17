package com.poc.controller;

import static io.restassured.RestAssured.given;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.poc.main.entity.UserDetails;
import com.poc.main.repository.UserDetailsRepository;
import com.poc.master.entity.Invoice;
import com.poc.master.repository.InvoiceRepository;

import io.restassured.RestAssured;
import io.restassured.response.Response;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class WelcomeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @MockBean
    private UserDetailsRepository userDetailsRepository;

    @MockBean
    private InvoiceRepository invoiceRepository;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;

        UserDetails userDetails = new UserDetails();
        userDetails.setMobileNumber("+919876543210");
        userDetails.setUsername("Krishna");
        userDetails.setActive('Y');
        userDetails.setGender("Male");
        userDetails.setLocation("Annojiguda");
        userDetails.setUserid(1L);
        userDetails.setRegistrationDate(new Timestamp(System.currentTimeMillis()));
        userDetails.setAge(20);

        Invoice invoice = new Invoice();
        invoice.setInvoiceId(2024061801L);
        invoice.setInvoiceDate(getCurrentDateWithoutTime());
        
        //later modify the Object[] mechanism in the repository layer itself as per MVP, we are doing here.
		Object[] paperOne=new Object[] {"0","+919876543210","Eenadu","Telugu","Telangana","Hyderabad","Uppal","06:30AM","10","10"};
		Object[] paperTwo=new Object[] {"1","+919876543210","Eenadu","Telugu","Telangana","Hyderabad","Ghatkesar","07:00AM","15","10"};
		Object[] paperThree=new Object[] {"2","+919876543210","Andhra Jyothi","Telugu","Telangana","Hyderabad","Uppal","07:30AM","10","10"};
		List<Object[]> paperList=new ArrayList<>();
		paperList.add(paperOne);
		paperList.add(paperTwo);
		paperList.add(paperThree);

        when(userDetailsRepository.findByMobileNumber(anyString())).thenReturn(Optional.of(userDetails));
        when(invoiceRepository.findByUserOrderByInvoiceDateDesc(userDetails)).thenReturn(Arrays.asList(invoice));
        when(userDetailsRepository.getUserDetailsByMobileNumber("+919876543210")).thenReturn(paperList);
    }

    @Test
    public void testWelcomeRequest_newUser() {
        Response response = given()
                .contentType("application/json")
                .body("{\"mobileNumber\": \"+919876543211\"}")
                .when()
                .post("/welcome");

        response.then().statusCode(200);
//        response.then().header("Content-Type", "application/pdf");
    }

    @Test
    public void testWelcomeRequest_existingUser() throws IOException {
        Response response = given()
                .contentType("application/json")
                .body("{\"mobileNumber\": \"+919876543210\"}")
                .when()
                .post("/welcome");

        response.then().statusCode(200);
        response.then().header("Content-Type", "application/pdf");

        byte[] pdfBytes = response.getBody().asByteArray();
        String testFilePath = "src/test/resources/files/downloaded_invoice.pdf";
        File pdfFile = new File(testFilePath);
        pdfFile.getParentFile().mkdirs(); 

        try (FileOutputStream fos = new FileOutputStream(pdfFile)) {
            fos.write(pdfBytes);
        }

        System.out.println("*****\n PDF file created at: " + pdfFile.getAbsolutePath()+"\n******\n");

       // pdfFile.deleteOnExit();// Optionally, delete on JVM exit
    }

    private static Date getCurrentDateWithoutTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    @SuppressWarnings("unused")
	private void scheduleFileDeletionForThirtyMinutes(String testFilePath) {
        new Timer(true).schedule(new TimerTask() {
            @Override
            public void run() {
                try { Files.deleteIfExists(Paths.get(testFilePath)); System.out.println("PDF file deleted: " + testFilePath);
                } catch (IOException e) { e.printStackTrace();}
            }
        }, 1800000); 
    }
}
