package com.nenerbener.driverSRT;
import java.lang.reflect.Method;
import org.junit.Test;
import static org.junit.Assert.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.LinkOption;

/**
 * Junit test class for SRTDriver.class
 * @author mm
 * @version 0.1
 * @since 06/23/2018
 */

public class DriverSRTTest {
	/** 
	 * Test a Youtube video with closed caption successfully creates a file
	 * 
	 * @throws Exception 
	 */
	@Test
	public void validURLWithClosedCaptionTest() throws Exception {

		String inputFile = "https://www.youtube.com/watch?v=I8XXfgF9GSc";
		String outputDir = "/tmp";
		
		Path outputPath = Paths.get(outputDir + "/I8XXfgF9GSc_0_en.srt");
		if (Files.exists(outputPath, new LinkOption[] {LinkOption.NOFOLLOW_LINKS})) Files.delete(outputPath);

		DriverSRT dsrt = new DriverSRT(
			inputFile,
			outputDir,
			true,
			false,
			false,
			true);
	
		outputPath = Paths.get(outputDir + "/I8XXfgF9GSc_0_en.srt");
//		outputPath = Paths.get(outputDir);
		if (Files.exists(outputPath, new LinkOption[] {LinkOption.NOFOLLOW_LINKS})) assertTrue(true);
		else assertTrue(false);
	}
	
	/** 
	 * Test a Youtube video with without closed caption does not create a file
	 * 
	 * @throws Exception 
	 */
	@Test
	public void validURLWithoutClosedCaptionTest() throws Exception {

		String inputFile = "https://www.youtube.com/watch?v=XOvlsrCv3Bk";
		String outputDir = "/tmp";
		
		Path outputPath = Paths.get(outputDir + "/XOvlsrCv3Bk_0_en.srt");
		if (Files.exists(outputPath, new LinkOption[] {LinkOption.NOFOLLOW_LINKS})) Files.delete(outputPath);

		DriverSRT dsrt = new DriverSRT(
			inputFile,
			outputDir,
			true,
			false,
			false,
			true);
	
		outputPath = Paths.get(outputDir + "/XOvlsrCv3Bk_0_en.srt");
//		outputPath = Paths.get(outputDir);
		if (Files.exists(outputPath, new LinkOption[] {LinkOption.NOFOLLOW_LINKS})) assertTrue(false);
		else assertTrue(true);
	}
	
	@Test
	public void urlExistsTest() {
		String arg = 
				"https://www.youtube.com/watch?v=I8XXfgF9GSc";
		boolean exists = DriverSRT.URLexists(arg);
		assertTrue(exists);
	}

}
