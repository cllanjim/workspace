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

	private static final String INPUT_IMAGE_FILE = "/testPicture.png";
	private static final String WATERMARK_FILE = "/testPicture.png";
	
	private static BufferedImage inputImage;
	private static BufferedImage watermark;
	private static BufferedImage testResultSequential;
	private static final int WM_PER_ROW = 10;
	
	private static IFilter pwfOne;
	private static IFilter pwfTwo;
	private static IFilter pwfOptimal;
	
	/**
	 * Testablauf:
	 * 
	 * 1) Setup dreier PWF: 1mal mit einem Kern, 1mal mit 2 Kernen, 1mal ohne Angabe -> optimale Zahl Kerne
	 * 2) Durchlauf vom 1-Kern-PWF -> liefert BufferedImage testResultSequential
	 * 3) Durchlauf von 2-Kern-PWF und optimal-PWF -> jeweils vergleich mit testResultSequential
	 * 
	 */
	
	@BeforeClass
	public static void setup() {
		inputImage = ParallelWatermarkFilterTest.loadImage(INPUT_IMAGE_FILE, "png");
	    watermark = ParallelWatermarkFilterTest.loadImage(WATERMARK_FILE, "png");
	    
	    pwfOne = new ParallelWatermarkFilter(watermark, WM_PER_ROW, 1);
	    pwfTwo = new ParallelWatermarkFilter(watermark, WM_PER_ROW, 2);
	    pwfOptimal = new ParallelWatermarkFilter(watermark, WM_PER_ROW);
	}
	
	@BeforeClass
	public static void runSequntially() {
		testResultSequential = pwfOne.apply(inputImage);
	}

	@Test
	public void runParallelOnTwoCores() {
		BufferedImage testResultTwoCores = pwfTwo.apply(inputImage);
		ParallelWatermarkFilterTest.compareImages(testResultSequential, testResultTwoCores, false);
	}
	
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
	private static BufferedImage loadImage(String file, String format) {
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
