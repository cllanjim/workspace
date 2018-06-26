package org.iMage.shutterpile.impl.supplier;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class TextWatermarkSupplier extends AbstractWatermarkSupplier{
	
	private final String watermarkString;
	private final Font font = new Font("TimesRoman", Font.PLAIN, 75);
	
	public TextWatermarkSupplier(String inputString) {
		this.watermarkString = inputString;
	}
	
	@Override
	public BufferedImage createWatermark() {
		BufferedImage temp = new BufferedImage(1,1,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = temp.createGraphics();
		FontMetrics metrics = g.getFontMetrics(this.font);
		Rectangle2D box = metrics.getStringBounds(this.watermarkString, g);

		int width = (int)box.getWidth();
		int height = (int)box.getHeight();
		
		BufferedImage watermark = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = watermark.createGraphics();
		
	    g2.setColor(Color.BLACK);
	    g2.setFont(this.font);
		g2.drawString(this.watermarkString, 0, height);
		
		g2.dispose();
		return watermark;
	}
	
	@Override
	public BufferedImage applyFilters(BufferedImage watermark) {
		return watermark;
	}
}
