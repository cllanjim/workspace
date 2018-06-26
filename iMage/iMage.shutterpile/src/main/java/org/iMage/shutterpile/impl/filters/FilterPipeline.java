package org.iMage.shutterpile.impl.filters;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

import org.iMage.shutterpile.port.IFilter;

public class FilterPipeline implements IFilter {

	private ArrayList<IFilter> filters;
	
	public FilterPipeline() {
		this.filters = new ArrayList<IFilter>();
	}
	
	/**
	 * Adds a single filter to the pipeline
	 * 
	 * @param newFilter
	 */
	public void addFilter(IFilter newFilter) {
		this.filters.add(newFilter);
	}
	
	/**
	 * Clears the pipeline of all filters.
	 */
	public void clearFilters() {
		this.filters.clear();
	}
	
	@Override
	public BufferedImage apply(BufferedImage inputImage) {
		// deep-copy the inputImage
		ColorModel colormodel = inputImage.getColorModel();
		boolean alpha = colormodel.isAlphaPremultiplied();
		WritableRaster raster = inputImage.copyData(null);
		BufferedImage modifiedImage = new BufferedImage(colormodel, raster, alpha, null);
		
		// apply filters from pipeline to image
		for(IFilter filter : this.filters) {
			modifiedImage = filter.apply(modifiedImage);
		}
		return modifiedImage;
	}

}
