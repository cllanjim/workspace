package org.iMage.shutterpile_parallel.impl.filters;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.impl.filters.WatermarkFilter;
import org.iMage.shutterpile.port.IFilter;

/**
 * The worker-thread used by {@link ParallelWatermarkFilter}.
 * 
 * @author lgavr
 *
 */
public class WatermarkWorker implements Runnable {

	private final BufferedImage watermark;
	private BufferedImage input;
	private int x;
	private int y;
	
	/**
	 * Constructor of this class.
	 * 
	 * @param watermark
	 * 			The watermark, which will be layed over the input image.
	 * @param input
	 * 			The image-fragment of the original image, on which this worker should work on.
	 * @param x
	 * 			The x-coordinate of the image-fragment, this worker is working on.
	 * @param y
	 * 			The according y-coordinate.
	 */
	public WatermarkWorker(BufferedImage watermark, BufferedImage input, int x, int y) {
		this.watermark = watermark;
		this.input = input;
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns the x-coordinate.
	 * 
	 * @return x
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Returns the y-coordinate.
	 * 
	 * @return y
	 */
	public int getY() {
		return y;
	}
	
	@Override
	public void run() {
		IFilter wmFilter = new WatermarkFilter(watermark, 1);
		input = wmFilter.apply(input);
	}
}
