package org.iMage.shutterpile_parallel.impl.filters;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

import org.iMage.shutterpile.impl.filters.WatermarkFilter;
import org.iMage.shutterpile.impl.util.ImageUtils;
import org.iMage.shutterpile.port.IFilter;

/**
 * A better (faster) implementation of the ParallelWatermarkFilter.
 * @author lgavr
 *
 */
public class BetterParallelWatermarkFilter implements IFilter {
	  private BufferedImage watermark;
	  private int watermarksPerRow;
	  private ThreadPoolExecutor threadPool;
	  private IFilter wmFilter;
	  
	/**
	 * Constructor for the BetterParallelWatermarkFilter.
	 * 
	 * @param watermark
	 * 			The watermark-image that is to be layed over the original image.
	 * @param watermarksPerRow
	 * 			The amount of watermarks per row.
	 * @param numThreads
	 * 			The desired amount of threads, the filter is supposed to run on.
	 */
	public BetterParallelWatermarkFilter(BufferedImage watermark, int watermarksPerRow, int numThreads) {
		if (numThreads == 1) {
			System.out.println("Watermark Filter initialized: 1 Core -> sequential version.");
			this.wmFilter = new WatermarkFilter(watermark, watermarksPerRow);
		} else if (numThreads > 1) {
			this.watermark = watermark;
			this.watermarksPerRow = watermarksPerRow;
			System.out.println("Watermark Filter initialized: " + numThreads + " Cores -> parallel version.");
			this.threadPool = (ThreadPoolExecutor) Executors.newFixedThreadPool(numThreads);
		}
	}
	
	/**
	 * Second constructor for the BetterParallelWatermarkFilter.
	 * This constructor calculates the optimal amount of threads according to system, the program is running on.
	 * 
	 * @param watermark
	 * 			The watermark-image that is to be layed over the original image.
	 * @param watermarksPerRow
	 * 			The amount of watermarks per row.
	 */
	public BetterParallelWatermarkFilter(BufferedImage watermark, int watermarksPerRow) {
		this(watermark, watermarksPerRow, calculateOptimalAmountOfThreads());
	}
	
	/**
	 * Calculates the optimal amount of threads according to the underlying system.
	 * 
	 * @return
	 */
	private static int calculateOptimalAmountOfThreads() {
		int numCores = Runtime.getRuntime().availableProcessors();
		return numCores;
	}
	
	@Override
	public BufferedImage apply(BufferedImage input) {
		if (wmFilter != null) {
			// case 1: sequential version
			return wmFilter.apply(input);
		} else {
			// case 2: parallel versions
		    int imgWidth = input.getWidth();
		    int imgHeight = input.getHeight();
	
		    int watermarkWidth = (int) Math.ceil((double) imgWidth / watermarksPerRow);		    
		    int watermarkHeight;
		    if (watermarkWidth <= 0) {
		      throw new IllegalArgumentException("watermark width would be too small");
		    }
		    watermark = ImageUtils.scaleWidth(watermark, watermarkWidth);
		    watermarkHeight = watermark.getHeight();
		    
		    int watermarksPerColumn = (int) Math.ceil((double) imgHeight / watermarkHeight);
		    
		    BufferedImage[][] result = new BufferedImage[watermarksPerRow][watermarksPerColumn];
		    
			for (int y = 0; y < watermarksPerColumn; y++) {
				for (int x = 0; x < watermarksPerRow; x++) {
					int w = x == watermarksPerRow - 1 
							? imgWidth - (watermarksPerRow - 1) * watermarkWidth : watermarkWidth;
					int h = y == watermarksPerColumn - 1 
							? imgHeight - (watermarksPerColumn - 1) * watermarkHeight : watermarkHeight;
							
					if (w != 0 && h != 0) {
						BufferedImage img = new BufferedImage(w, h, input.getType());
						Graphics2D graphics = (Graphics2D) img.getGraphics();
						graphics.drawImage(input.getSubimage(x * watermarkWidth, y * watermarkHeight, w, h),
								null, 0, 0);						
						result[x][y] = img;
					}
					threadPool.execute(new WatermarkWorker(watermark, result[x][y], result[x][y], 0, 0));
				}
			}
			threadPool.shutdown();
		    while (!threadPool.isTerminated()) {}
			return combineFragments(result, imgWidth, imgHeight);
		}
	}

	private BufferedImage combineFragments(BufferedImage[][] input, int width, int height) {
		BufferedImage result = new BufferedImage(width, height, input[0][0].getType());
		Graphics2D graphics = (Graphics2D) result.getGraphics();
		
		int posX = 0;
		int posY = 0;
		for (int y = 0; y < input[0].length; y++) {
			int h = 0;
			for (int x = 0; x < input.length; x++) {
				if (input[x][y] != null) {
					BufferedImage img = input[x][y];
					graphics.drawImage(img, null, posX, posY);
					posX += img.getWidth();
					h = img.getHeight();
				}
			}
			posY += h;
			posX = 0;
		}
		return result;
	}

}
