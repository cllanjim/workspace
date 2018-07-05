package org.iMage.shutterpile_parallel.impl;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.metadata.IIOMetadata;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageInputStream;
import javax.imageio.stream.ImageOutputStream;

import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile_parallel.impl.filters.ParallelWatermarkFilter;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Tests the ParallelWstermarkFilter
 * 
 * @author lgavr
 *
 */
public class ParallelWatermarkFilterTest extends TestBase {
	
	private static ParallelWatermarkFilter filter;
	private static final File TEST_DIR = new File("target/testData");
	private static final String IMAGE_FILE = "/testPicture.png";
	private static final String WATERMARK_FILE = "/watermark.png";
	private static BufferedImage testImage;
	private static BufferedImage watermark;
	private static BufferedImage resultImage;
	private IIOMetadata imeta;
	
	
	/**
	 * Setup the test
	 */
	@Before
	public void setup() {	
		// Read the original image
		try (ImageInputStream iis = ImageIO.createImageInputStream(
				this.getClass().getResourceAsStream(IMAGE_FILE));) {
			ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
			reader.setInput(iis, true);
			ImageReadParam params = reader.getDefaultReadParam();
			testImage = reader.read(0, params);
			imeta = reader.getImageMetadata(0);
			reader.dispose();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		// Read the watermark image
		try (ImageInputStream iis = ImageIO.createImageInputStream(
				this.getClass().getResourceAsStream(WATERMARK_FILE));) {
			ImageReader reader = ImageIO.getImageReadersByFormatName("png").next();
			reader.setInput(iis, true);
			ImageReadParam params = reader.getDefaultReadParam();
			watermark = reader.read(0, params);
			reader.dispose();
		} catch (IOException e) {
			fail(e.getMessage());
		}
		
		filter = new ParallelWatermarkFilter(watermark, 10, 3);
	}
	
	/**
	 * Save the file.
	 */
	@After
	public void tearDown() {
		SimpleDateFormat sdf = new SimpleDateFormat("HHmmss_SSS");
	    String time = sdf.format(new Date());

	    File outputFile = new File(TEST_DIR + "/resultImage_" + time + ".png");

	    if (resultImage != null) {
	      try (FileOutputStream fos = new FileOutputStream(outputFile);
	          ImageOutputStream ios = ImageIO.createImageOutputStream(fos);) {
	        ImageWriter writer = ImageIO.getImageWritersByFormatName("png").next();
	        writer.setOutput(ios);

	        ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault());
	        iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT); // mode explicit necessary

	        // set PNG Quality
	        iwparam.setCompressionQuality(1f);
	        writer.write(imeta, new IIOImage(resultImage, null, null), iwparam);
	        writer.dispose();
	      } catch (IOException e) {
	        fail();
	      }
	    }
	  }

	/**
	 * Create a watermarked image.
	 */
	@Test
	public void applyWatermark() {
		Date start = new Date();
		resultImage = filter.apply(testImage);
		Date end = new Date();
		long diff = end.getTime() - start.getTime();
		System.out.println("Duration: " + diff);
	}
}
