package org.iMage.shutterpile.impl.supplier;

import java.awt.Color;
import java.awt.image.BufferedImage;
import org.iMage.shutterpile.impl.filters.GrayscaleFilter;
import org.iMage.shutterpile.impl.filters.ThresholdFilter;

import org.iMage.shutterpile.port.IWatermarkSupplier;

/**
 * This class realizes a {@link IWatermarkSupplier} which uses a {@link BufferedImage} to generate
 * the watermark.
 *
 * @author Dominic Wolff
 *
 */
public final class ImageWatermarkSupplier implements IWatermarkSupplier {
	
	private BufferedImage watermark;
	private int width;
	private int height;
	
	private GrayscaleFilter gsFilter;
	private ThresholdFilter thFilter;
  /**
   * Create the {@link IWatermarkSupplier} by base image of watermark.
   *
   * @param watermarkInput
   *          the base image to create the watermark
   */
  public ImageWatermarkSupplier(BufferedImage watermarkInput) {
	watermark = watermarkInput;
	width = watermarkInput.getWidth();
	height = watermarkInput.getHeight();
	
	gsFilter = new GrayscaleFilter();
	thFilter = new ThresholdFilter();
  }

  @Override
  public BufferedImage getWatermark() {
	watermark = gsFilter.apply(watermark);
	watermark = thFilter.apply(watermark);

    boolean hasAlpha = watermark.getColorModel().hasAlpha();
    
	BufferedImage outWatermark = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
    
    for (int x = 0; x < width; x++) {
        for (int y = 0; y < height; y++) {
        	Color pixel = new Color(watermark.getRGB(x, y));
        	float red = pixel.getRed();
        	float green = pixel.getGreen();
        	float blue = pixel.getBlue();
        	float alpha = 255;
        	if (hasAlpha) {
        		alpha = pixel.getAlpha();
        	}
        	alpha /= 2;
        	int newPixel = new Color(red / 255, green / 255, blue / 255, alpha / 255).getRGB();
        	outWatermark.setRGB(x, y, newPixel);
        }
    }
    return outWatermark;
  }

}
