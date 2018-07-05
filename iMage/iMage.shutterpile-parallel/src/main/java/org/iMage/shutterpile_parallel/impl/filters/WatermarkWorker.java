package org.iMage.shutterpile_parallel.impl.filters;

import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

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
	private final BufferedImage input;
	private BufferedImage result;
	private int x;
	private int y;
	
	/**
	 * Constructor of this class.
	 * 
	 * @param watermark
	 * 			The watermark, which will be layed over the input image.
	 * @param input
	 * 			The image-fragment of the original image, on which this worker should work on.
	 * @param result
	 * 			The resulting image.
	 * @param x
	 * 			The x-coordinate of the image-fragment, this worker is working on.
	 * @param y
	 * 			The according y-coordinate.
	 */
	public WatermarkWorker(BufferedImage watermark, BufferedImage input, BufferedImage result, int x, int y) {
		this.watermark = watermark;
		this.input = input;
		this.result = result;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void run() {
		this.mergeImages(input, watermark, x, y, result);
	}
	
	  /**
	   * Draw a small image to a bigger image using alpha compositing.
	   *
	   * @param source
	   *          the source image (large image)
	   * @param toDraw
	   *          the image to be drawn on top of <em>source</em>
	   * @param x
	   *          the start index (horizontal) in <em>source</em>
	   * @param y
	   *          the start index (vertical) in <em>source</em>
	   * @param target
	   *          a target image (same size as <em>source</em>) to draw the new image
	   */
	  private void mergeImages(BufferedImage source, BufferedImage toDraw, int x, int y, BufferedImage target) {
	    int sW = source.getWidth();
	    int sH = source.getHeight();
	    int dW = toDraw.getWidth();
	    int dH = toDraw.getHeight();

	    for (int w = 0; w < dW && w + x < sW; w++) {
	      for (int h = 0; h < dH && h + y < sH; h++) {
	        target.setRGB(x + w, y + h, this.merge(toDraw.getRGB(w, h), source.getRGB(x + w, y + h)));
	      }
	    }
	  }

	  /**
	   * Calculate color after draw A over B (see:
	   * <a href="https://de.wikipedia.org/wiki/Alpha_Blending">Alpha Blending</a>).
	   *
	   * @param inputA
	   *          the color (ARGB) which shall drawn over <em>B</em>
	   * @param inputB
	   *          the color (ARGB) that is painted over <em>A</em>
	   * @return the result color (ARGB)
	   */
	  private int merge(int inputA, int inputB) {
	    int aA = (inputA >> 24 & 0x000000FF);
	    int rA = (inputA >> 16 & 0x000000FF);
	    int gA = (inputA >> 8 & 0x000000FF);
	    int bA = (inputA & 0x000000FF);

	    int aB = (inputB >> 24 & 0x000000FF);
	    int rB = (inputB >> 16 & 0x000000FF);
	    int gB = (inputB >> 8 & 0x000000FF);
	    int bB = (inputB & 0x000000FF);

	    int a = (255 * aA + (255 - aA) * aB) / 255;
	    // If fully transparent do nothing
	    if (a == 0) {
	      return 0x00000000;
	    }

	    int r = (255 * aA * rA + (255 - aA) * aB * rB) / (255 * a);
	    int g = (255 * aA * gA + (255 - aA) * aB * gB) / (255 * a);
	    int b = (255 * aA * bA + (255 - aA) * aB * bB) / (255 * a);

	    return a << 24 | r << 16 | g << 8 | b;
	  }
}
