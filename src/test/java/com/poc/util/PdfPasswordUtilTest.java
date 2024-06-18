package com.poc.util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.itextpdf.io.exceptions.IOException;

@SpringJUnitConfig
public class PdfPasswordUtilTest {
	
	@Autowired
    private ResourceLoader resourceLoader;
	
	@Test
    public void testPasswordPDFfile() throws IOException, java.io.IOException {
		
        Resource resource = resourceLoader.getResource("classpath:files/Eenadu.pdf");
        File srcFile = resource.getFile();
        String srcPath = srcFile.getAbsolutePath();
        
        Path destPath = Paths.get("src/test/resources/protected");
        File destDir = destPath.toFile();
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
		
		List<String> listOfUsers=List.of("1234567890");
		String ownerPassword="1234567890";
		
		String todayDate = LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMMMYYYY"));
		String newspaperName = srcPath.endsWith(".pdf") ? srcPath.substring(srcPath.lastIndexOf("\\") + 1) : null;
		
		String fileName=todayDate+" "+newspaperName;
		
		PdfPasswordUtil.createPasswordProtectedPdfsForUsers(srcPath.toString(), destDir.toString(), listOfUsers, ownerPassword, fileName);
		
	}

}
