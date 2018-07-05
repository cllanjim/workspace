package org.iMage.shutterpile_parallel.impl.filters;

import org.iMage.shutterpile.port.IFilter;

import sun.jvm.hotspot.utilities.WorkerThread;

import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Parallel implementation 
 * 
 * @author lgavr
 *
 */
public class ParallelWatermarkFilter implements IFilter {
	  private final BufferedImage watermark;
	  private int watermarksPerRow;
	  private ExecutorService threadPool;
	  
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
		
		if (numThreads == 1) {
			// TODO use the sequential algorithm
		} else {
			this.threadPool = Executors.newFixedThreadPool(numThreads);
		}
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
		return 1;
	}
	
	@Override
	public BufferedImage apply(BufferedImage input) {
		for (int i = 0; i < 10; i++) {
	        Runnable worker = new WatermarkFilterWorkerThread(watermark);
	        threadPool.execute(worker);
	      }
		threadPool.shutdown();
	    while (!threadPool.isTerminated()) {
	    }
		return null;
	}
	
	private BufferedImage applyToPart(BufferedImage inputPart) {
		
		return inputPart;
	}

}
