package org.iMage.shutterpile_parallel.impl;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile_parallel.impl.filters.ParallelWatermarkFilter;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the ParallelWstermarkFilter
 * 
 * @author lgavr
 *
 */
public class ParallelWatermarkFilterTest extends TestBase {

	private static final String INPUT_IMAGE_FILE = "/testPicture.jpg";
	private static final String WATERMARK_FILE = "/watermark.png";
	
	private static BufferedImage inputImage;
	private static BufferedImage watermark;
	private static BufferedImage testResultSequential;
	private static final int WM_PER_ROW = 10;
	
	private static IFilter pwfOne;
	private static IFilter pwfTwo;
	private static IFilter pwfOptimal;
	
	/**
	 * set up the filters and test-images
	 */
	
	@BeforeClass
	public static void setup() {
		inputImage = ParallelWatermarkFilterTest.loadImage(INPUT_IMAGE_FILE, "jpg");
	    watermark = ParallelWatermarkFilterTest.loadImage(WATERMARK_FILE, "png");
	    
	    pwfOne = new ParallelWatermarkFilter(watermark, WM_PER_ROW, 1);
	    pwfTwo = new ParallelWatermarkFilter(watermark, WM_PER_ROW, 2);
	    pwfOptimal = new ParallelWatermarkFilter(watermark, WM_PER_ROW);
	}
	
	/**
	 * run the watermark filter using one core -> sequentially
	 */
	@BeforeClass
	public static void runSequntially() {
		testResultSequential = pwfOne.apply(inputImage);
	}

	/**
	 * run the watermark filter using two cores -> parallel
	 */
	@Test
	public void runParallelOnTwoCores() {
		BufferedImage testResultTwoCores = pwfTwo.apply(inputImage);
		ParallelWatermarkFilterTest.compareImages(testResultSequential, testResultTwoCores, false);
	}
	
	/**
	 * run the watermark filter using the optimal amount of cores
	 */
	@Test
	public void runParallelOnOptimalAmountOfCores() {
		BufferedImage testResultOptimalAmountOfCores = pwfOptimal.apply(inputImage);
		ParallelWatermarkFilterTest.compareImages(testResultSequential, testResultOptimalAmountOfCores, false);
	}
	
	
	/**
	 * Loads an image-file into a BufferedImage.
	 * @param file
	 * 			The file to load.
	 * @param format
	 * 			The format to load the file in.
	 * @return
	 * 			The loaded BufferedImage.
	 */
	public static BufferedImage loadImage(String file, String format) {
	    try (ImageInputStream iis = ImageIO
	        .createImageInputStream(ParallelWatermarkFilterTest.class.getResourceAsStream(file));) {
	      ImageReader reader = ImageIO.getImageReadersByFormatName(format).next();
	      reader.setInput(iis, true);
	      ImageReadParam params = reader.getDefaultReadParam();
	      BufferedImage image = reader.read(0, params);
	      reader.dispose();
	      return image;
	    } catch (IOException e) {
	      fail(e.getMessage());
	      return null;
	    }
	}
	
}
