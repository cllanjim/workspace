package org.iMage.shutterpile.impl.filters;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.After;
import org.junit.Test;

/**
 * Sommersemester 2018 - Übungsblatt 2 - Aufgabe 4 - a
 */
public class GrayscaleFilterTest {

	private GrayscaleFilter gsFilter;
	private int imageHeight;
	private int imageWidth;
	private static final File TEST_DIR = new File("target/dataTest");
	private static final File IMAGE_FILE = new File("src/test/resources/pearWatermark_input_alpha.png");
	private static final File REF_FILE = new File("src/test/resources/pearWatermark.png");

	private static BufferedImage testImage;
	private static BufferedImage refImage;
	private BufferedImage grayedImageTestResult;

	/**
	 * Do once at start of test-run
	 * 
	 * Check if test-directory exists.  
	 * If so, empty it. If not, create it. 
	 * 
	 * Also load the reference Image.
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

	  refImage = null;
	  try {
		  refImage = ImageIO.read(REF_FILE);
	  } catch (IOException e) {
		  fail(e.getMessage());
	  }
	  
	}
	
	/**
	 * Do once before each test-case
	 * 
	 * Load the test image and get it's dimensions.
	 */

	@Before
	public void setUp() {
		gsFilter = new GrayscaleFilter();
		
		testImage = null;
		grayedImageTestResult = null;
		
		try {
			testImage = ImageIO.read(IMAGE_FILE);	
			imageHeight = testImage.getHeight();
			imageWidth = testImage.getWidth();
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
		
		File outputFile = new File(TEST_DIR + "/grayedImage_" + time + ".png");
		
		if (grayedImageTestResult != null) {
			try {
				ImageIO.write(grayedImageTestResult, "png", outputFile);
			} catch (IOException e) {
				fail();
			}
				
			
		}
	}

	/**
	 * Check if generated image has alpha channel.
	 */
	@Test
	public void shouldHaveAlphaChannel() {
		grayedImageTestResult = gsFilter.apply(testImage);
		assertTrue(grayedImageTestResult.getColorModel().hasAlpha());
	}
}
