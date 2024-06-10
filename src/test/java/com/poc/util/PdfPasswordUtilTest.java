package com.poc.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.itextpdf.io.exceptions.IOException;

public class PdfPasswordUtilTest {
	
	@Test
    public void testPasswordPDFfile() throws IOException, java.io.IOException {
		String srcPath="C:\\Users\\Gaganam Krishna\\Downloads\\Eenadu-TS 29-05.pdf";
		String destDir="C:\\Users\\Gaganam Krishna\\Downloads\\protected\\";
		List<String> listOfUsers=List.of("1234567890");
		String ownerPassword="1234567890";
		
		String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMMMYYYY"));
		String newspaperName = srcPath.endsWith(".pdf") ? srcPath.substring(srcPath.lastIndexOf("\\") + 1) : null;
		
		String fileName=todayDate+" "+newspaperName;
		
		PdfPasswordUtil.createPasswordProtectedPdfsForUsers(srcPath, destDir, listOfUsers, ownerPassword, fileName);
		
	}

}
