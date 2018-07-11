package org.iMage.shutterpile_parallel.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.time.StopWatch;
import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile_parallel.impl.filters.ParallelWatermarkFilter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * UB6 A4 e)
 * Der Test stellt sicher, dass die Ausführung des parallelen Filters nicht
 * länger dauert, als die sequentielle.
 * 
 * @author lgavr
 *
 */
public class CheckDuration extends TestBase {
	
	private static final String INPUT_IMAGE_FILE = "/testPicture.jpg";
	private static final String WATERMARK_FILE = "/watermark.png";
	
	private static BufferedImage inputImage;
	private static BufferedImage watermark;
	private static final int WM_PER_ROW = 30;
	
	private static IFilter pwfSequential;
	private static IFilter pwfParallel;

	private long sequentialTime;
	private static StopWatch sw;
	
	@BeforeClass
	public static void setup() throws IOException {
	    inputImage = ImageIO.read(new File("src/test/resources/" + INPUT_IMAGE_FILE));
	    watermark = ImageIO.read(new File("src/test/resources/" + WATERMARK_FILE));
	    
	    pwfSequential = new ParallelWatermarkFilter(watermark, WM_PER_ROW, 1);
	    pwfParallel = new ParallelWatermarkFilter(watermark, WM_PER_ROW);
	    
	    sw = new StopWatch();
	}
	
	@Before
	public void timeSequential() {
		sw.reset();
		sw.start();
		pwfSequential.apply(inputImage);
		sw.stop();
		sequentialTime = sw.getNanoTime();
	}
	
	@Test(timeout=1000)
	public void timeParallel() {
		sw.reset();
		sw.start();
		pwfParallel.apply(inputImage);
		sw.stop();
		Assert.assertTrue("Parallel dauert länger als Sequentiell. "
				+ "(s: " + sequentialTime + ", p: " + sw.getNanoTime() + ")",
				sequentialTime > sw.getNanoTime());
	}
	

}
