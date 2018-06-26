package org.iMage.shutterpile.impl.supplier;

import java.awt.image.BufferedImage;
import java.util.concurrent.ThreadLocalRandom;

public class RandomWatermarkSupplier extends AbstractWatermarkSupplier {

	private TextWatermarkSupplier twms;
	
	public RandomWatermarkSupplier() {
		int randInt = ThreadLocalRandom.current().nextInt(65, 90 + 1);
		String randChar = Character.toString((char)randInt);
		twms = new TextWatermarkSupplier(randChar);
	}

	@Override
	public BufferedImage createWatermark() {
		return this.twms.createWatermark();
	}

	@Override
	public BufferedImage applyFilters(BufferedImage watermark) {
		return watermark;
	}

}
