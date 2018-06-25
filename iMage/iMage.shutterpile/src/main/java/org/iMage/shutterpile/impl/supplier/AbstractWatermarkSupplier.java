package org.iMage.shutterpile.impl.supplier;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.port.IWatermarkSupplier;

public abstract class AbstractWatermarkSupplier implements IWatermarkSupplier{

	private BufferedImage createdWatermark;

    public AbstractWatermarkSupplier() {};
	
	@Override
	public BufferedImage getWatermark() {
	    if (this.createdWatermark == null) {
	    	BufferedImage watermark = this.createWatermark();
	    	watermark = this.applyFilters(watermark);
	        this.createdWatermark = watermark;
	      }
	      return this.createdWatermark;
	}
	
	public abstract BufferedImage createWatermark();
	public abstract BufferedImage applyFilters(BufferedImage watermark);

}
