package org.iMage.shutterpile.impl.supplier;

import java.awt.image.BufferedImage;

public class TextWatermarkSupplier extends AbstractWatermarkSupplier{
	
	private final String watermarkString;
	
	public TextWatermarkSupplier(String inputString) {
		this.watermarkString = inputString;
	}
	
	public BufferedImage createWatermark() {
		BufferedImage watermark = new BufferedImage(200,100,BufferedImage.TYPE_INT_ARGB);
		watermark.createGraphics().drawString(this.watermarkString, 0, 0);
		return watermark;
	}
	
	public BufferedImage applyFilters(BufferedImage watermark) {
		return watermark;
	}
}
