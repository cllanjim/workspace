package org.iMage.shutterpile.impl.filters;

import java.awt.image.BufferedImage;
import java.awt.Color;
import org.iMage.shutterpile.port.IFilter;

/**
 * This Filter adds transparency to an image according to its color values.
 * 
 * @author Lars Gavris
 *
 */
public final class ThresholdFilter implements IFilter {
	private int threshold;
	
	/**
	 * Initialize the filter.
	 * 
	 * @param threshold = Integer : 0..255
	 */
	public ThresholdFilter(int... threshold) {
		if (threshold.length == 0) {
			this.threshold = 127;
		} else if (threshold.length == 1 && (threshold[0] < 0 || threshold[0] > 255)) {
			throw new IllegalArgumentException("threshold must be in range 0..255");
		} else {
			this.threshold = threshold[0];
		}
	}
	/**
	 * Sets the threshold of the filter.
	 * @param threshold = Integer
	 */
	public void setThreshold(int threshold) {
		if (threshold < 0 || threshold > 255) {
			throw new IllegalArgumentException("threshold must be in range 0..255");
		}
		this.threshold = threshold;
	}
	
	/**
	 * Applies the filter algorithm.
	 * 
	 * @param input = BufferedImage
	 * 
	 * @return BufferedImage
	 */
	public BufferedImage apply(BufferedImage input) {
		
	    int width = input.getWidth();
	    int height = input.getHeight();
	    boolean hasAlpha = input.getColorModel().hasAlpha();
	    
	    BufferedImage thresholdImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	        	Color pixel = new Color(input.getRGB(x, y));
	        	float red = pixel.getRed();
	        	float green = pixel.getGreen();
	        	float blue = pixel.getBlue();
	        	float mean = (red + green + blue) / 3;
	        	float alpha = 255;
	        	if (hasAlpha) {
	        		alpha = pixel.getAlpha();
	        	}
	        	if (mean > this.threshold) {
	        		alpha = 0;
	        	}
	        	int thresholdPixel = new Color(red / 255, green / 255, blue / 255, alpha / 255).getRGB();
	        	thresholdImage.setRGB(x, y, thresholdPixel);
	        }
	    }
		return thresholdImage;
	}
}
