package org.iMage.shutterpile.impl.filters;

import java.awt.image.BufferedImage;
import java.awt.Color;
import org.iMage.shutterpile.port.IFilter;

/**
 * The gray-scale Filter sets each pixels color-value to the mean 
 * of its red, green, and blue value.
 * 
 * @author Lars Gavris
 *
 */
public final class GrayscaleFilter implements IFilter {
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
	    
	    BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

	    for (int x = 0; x < width; x++) {
	        for (int y = 0; y < height; y++) {
	        	Color pixel = new Color(input.getRGB(x, y));
	        	float mean = (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
	        	float alpha = 255;
	        	if (hasAlpha) {
	        		alpha = pixel.getAlpha();
	        	}
	        	int grayPixel = new Color(mean / 255, mean / 255, mean / 255, alpha / 255).getRGB();
	        	grayImage.setRGB(x, y, grayPixel);
	        }
	    }
	    return grayImage;
    }
}
