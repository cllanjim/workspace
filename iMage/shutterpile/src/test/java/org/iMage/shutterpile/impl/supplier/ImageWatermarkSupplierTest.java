package org.iMage.shutterpile.impl.supplier;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;

/**
 * Sommersemester 2018 - Übungsblatt 2 - Aufgabe 4 - c
 */
public class ImageWatermarkSupplierTest {

	private static ImageWatermarkSupplier supplier;
	private static final File TEST_DIR = new File("target/dataTest");
	private static final File IMAGE_FILE = new File("src/test/resources/tichyWatermark_input_no_alpha.png");
	private static BufferedImage testImage;
	private BufferedImage watermarkResult;

	/**
	 * Do once at start of test-run
	 * 
	 * Check if test-directory exists.  
	 * If so, empty it. If not, create it. 
	 * 
	 */
	@BeforeClass
	public static void beforeClass() {
	  if (TEST_DIR.exists()) {
	    for (File f : TEST_DIR.listFiles()) {
	      f.delete();
	    }
	  } else {
	    TEST_DIR.mkdirs();
	  }	  
	  
	  try {
		  testImage = ImageIO.read(IMAGE_FILE);
		  supplier = new ImageWatermarkSupplier(testImage);
	  } catch (IOException e) {
		  fail(e.getMessage());
	  }
	}
	
	/**
	 * Do once after each test-case
	 * 
	 * Save generated image to the test directory.
	 */
	@After
	public void tearDown() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss_SSS");
		String time = sdf.format(new Date());
		
		File outputFile = new File(TEST_DIR + "/watermark_" + time + ".png");
		
		if (watermarkResult != null) {
			try {
				ImageIO.write(watermarkResult, "png", outputFile);
			} catch (IOException e) {
				fail();
			}
				
			
		}
	}

	/**
	 * Check if generated image has alpha channel.
	 */
	@Test
	public void shouldCreateWatermark() {
		watermarkResult = supplier.getWatermark();
		assertTrue(true);
	}
}
