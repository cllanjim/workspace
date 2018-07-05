package org.iMage.shutterpile_parallel.impl.filters;

import org.iMage.shutterpile.impl.util.ImageUtils;
import org.iMage.shutterpile.port.IFilter;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Parallel implementation 
 * 
 * @author lgavr
 *
 */
public class ParallelWatermarkFilter implements IFilter {
	  private final BufferedImage watermark;
	  private int watermarksPerRow;
	  private ThreadPoolExecutor threadPool;
	  
	/**
	 * Constructor for the ParallelWatermarkFilter.
	 * 
	 * @param watermark
	 * 			The watermark-image that is to be layed over the original image.
	 * @param watermarksPerRow
	 * 			The amount of watermarks per row.
	 * @param numThreads
	 * 			The desired amount of threads, the filter is supposed to run on.
	 */
	public ParallelWatermarkFilter(BufferedImage watermark, int watermarksPerRow, int numThreads) {
		this.watermark = watermark;
		this.watermarksPerRow = watermarksPerRow;
		
		this.threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
		
	}
	
	/**
	 * Second constructor for the ParallelWatermarkFilter.
	 * This constructor calculates the optimal amount of threads according to system, the program is running on.
	 * 
	 * @param watermark
	 * 			The watermark-image that is to be layed over the original image.
	 * @param watermarksPerRow
	 * 			The amount of watermarks per row.
	 */
	public ParallelWatermarkFilter(BufferedImage watermark, int watermarksPerRow) {
		this(watermark, watermarksPerRow, calculateOptimalAmountOfThreads());
	}
	
	/**
	 * Calculates the optimal amount of threads according to the underlying system.
	 * 
	 * @return
	 */
	private static int calculateOptimalAmountOfThreads() {
		return 4;
	}
	
	@Override
	public BufferedImage apply(BufferedImage input) {
	    int imgWidth = input.getWidth();
	    int imgHeight = input.getHeight();

	    int watermarkWidth = imgWidth / this.watermarksPerRow;
	    int watermarkHeight;
	    if (watermarkWidth <= 0) {
	      throw new IllegalArgumentException("watermark width would be too small");
	    }
	    BufferedImage watermark = ImageUtils.createARGBImage(this.watermark);
	    watermark = ImageUtils.scaleWidth(watermark, watermarkWidth);
	    watermarkHeight = watermark.getHeight();

	    BufferedImage result = new BufferedImage(imgWidth, imgHeight, BufferedImage.TYPE_INT_ARGB);
	    
	    int i = 0;
		for (int x = 0; x < imgWidth; x += watermarkWidth) {
			for (int y = 0; y < imgHeight; y += watermarkHeight) {
				threadPool.execute(new WatermarkWorker(watermark, input, result, x, y));
			}
		}
		threadPool.shutdown();
	    while (!threadPool.isTerminated()) {}
		return result;
	}
}
