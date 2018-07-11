package org.iMage.shutterpile_parallel.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import org.iMage.shutterpile.impl.filters.WatermarkFilter;
import org.iMage.shutterpile.impl.supplier.ImageWatermarkSupplier;
import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile.port.IWatermarkSupplier;
import org.iMage.shutterpile_parallel.impl.filters.BetterParallelWatermarkFilter;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Tests the ParallelWstermarkFilter
 * 
 * @author lgavr
 *
 */
public class BetterParallelWatermarkFilterTest extends TestBase {

	private static final String INPUT_IMAGE_FILE = "/testPicture.jpg";
	private static final String WATERMARK_FILE = "/watermark.png";
	
	private static BufferedImage inputImage;
	private static BufferedImage watermark;
	private static final int WM_PER_ROW = 10;
	
	private static IFilter betterParallelFilter;
	private static IFilter sequentialFilter;
	
	/**
	 * set up the filters and test-images
	 * 
	 * @throws IOException
	 * 			May throw an IOException.
	 */
	
	@BeforeClass
	public static void setup() throws IOException {
	    inputImage = ImageIO.read(new File("src/test/resources/" + INPUT_IMAGE_FILE));
	    watermark = ImageIO.read(new File("src/test/resources/" + WATERMARK_FILE));
	    
	    IWatermarkSupplier supplier = new ImageWatermarkSupplier(watermark);
	    watermark = supplier.getWatermark();
	    
	    
	    betterParallelFilter = new BetterParallelWatermarkFilter(watermark, WM_PER_ROW);
	    sequentialFilter = new WatermarkFilter(watermark, WM_PER_ROW);
	    System.out.println("");
	}

	
	/**
	 * run the new parallelFilter
	 * 
	 * @throws IOException
	 * 			May throw an IOException.
	 */
	@Test
	public void runFilters() throws IOException {

		BufferedImage resultS = sequentialFilter.apply(inputImage);		
		File outputfileS = new File("./target/testData/imageS.png");
		ImageIO.write(resultS, "png", outputfileS);
		
		BufferedImage resultP = betterParallelFilter.apply(inputImage);
		File outputfileP = new File("./target/testData/imageP.png");
		ImageIO.write(resultP, "png", outputfileP);
		
		TestBase.compareImages(resultS, resultP, false);
	}
}
