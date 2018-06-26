package org.iMage.shutterpile.impl.filters;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.impl.supplier.ImageWatermarkSupplier;
import org.iMage.shutterpile.port.IFilter;

public class AlphaFilter implements IFilter {
	
	private int threshold = 50;
	
	@Override
	  public final BufferedImage apply(BufferedImage watermark) {
	    for (int i = 0; i < watermark.getWidth(); i++) {
	      for (int q = 0; q < watermark.getHeight(); q++) {
	        int color = watermark.getRGB(i, q);
	        int alpha = color >> 24 & 0x000000FF;
	        alpha = (alpha * this.threshold) / 100;
	        watermark.setRGB(i, q, (color & 0x00FFFFFF) | (alpha << 24));
	      }
	    }
	    watermark.flush();
	    return watermark;
	  }
	
	public void setThreshold(int threshold) {
		if(threshold >= 0 && threshold <= 255) this.threshold = threshold;
	}
}
