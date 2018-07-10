package org.iMage.shutterpile_parallel.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.time.StopWatch;

import org.iMage.shutterpile.impl.filters.WatermarkFilter;
import org.iMage.shutterpile.impl.supplier.ImageWatermarkSupplier;
import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile.port.IWatermarkSupplier;
import org.iMage.shutterpile_parallel.impl.filters.BetterParallelWatermarkFilter;
import org.iMage.shutterpile_parallel.impl.filters.ParallelWatermarkFilter;
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
	private static IFilter parallelFilter;
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
	    
	    betterParallelFilter = new BetterParallelWatermarkFilter(supplier.getWatermark(), WM_PER_ROW, 1);
	    parallelFilter = new ParallelWatermarkFilter(supplier.getWatermark(), WM_PER_ROW, 1);
	    sequentialFilter = new WatermarkFilter(supplier.getWatermark(), WM_PER_ROW);
	    System.out.println("");
	}
	
	/**
	 * run the better ParallelFilter
	 * 
	 * @throws IOException
	 * 			May throw an IOException.
	 */
	@Test
	public void runFilter() throws IOException {
	    StopWatch sw = new StopWatch();
	    sw.reset();
	    sw.start();
		BufferedImage result = betterParallelFilter.apply(inputImage);
		sw.stop();
		System.out.println("Better Parallel: " + sw.getNanoTime());
		
		File outputfile = new File("./target/testData/image.png");
		ImageIO.write(result, "png", outputfile);
	}
	
	/**
	 * run the parallelFilter
	 * 
	 * @throws IOException
	 * 			May throw an IOException.
	 */
	@Test
	public void runFilter2() throws IOException {
	    StopWatch sw = new StopWatch();
	    sw.reset();
	    sw.start();
		BufferedImage result = parallelFilter.apply(inputImage);
		sw.stop();
		System.out.println("Old Parallel:    " + sw.getNanoTime());
		
		
		File outputfile = new File("./target/testData/image2.png");
		ImageIO.write(result, "png", outputfile);
	}
	
	/**
	 * run the sequentialFilter
	 * 
	 * @throws IOException
	 * 			May throw an IOException.
	 */
	@Test
	public void runSequential() throws IOException {
	    StopWatch sw = new StopWatch();
	    sw.reset();
	    sw.start();
		BufferedImage result = sequentialFilter.apply(inputImage);
		sw.stop();
		System.out.println("Sequential:      " + sw.getNanoTime());
		
		
		File outputfile = new File("./target/testData/imageS.png");
		ImageIO.write(result, "png", outputfile);
	}
}
