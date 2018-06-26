package org.iMage.shutterpile.impl.supplier;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.impl.filters.GrayscaleFilter;
import org.iMage.shutterpile.impl.filters.ThresholdFilter;
import org.iMage.shutterpile.impl.filters.AlphaFilter;
import org.iMage.shutterpile.impl.util.ImageUtils;
import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile.port.IWatermarkSupplier;

/**
 * This class realizes a {@link IWatermarkSupplier} which uses a {@link BufferedImage} to generate
 * the watermark.
 *
 * @author Dominik Fuchss
 *
 */
public final class ImageWatermarkSupplier extends AbstractWatermarkSupplier{
	
  private final IFilter gsf = new GrayscaleFilter();
  private final IFilter thf = new ThresholdFilter();
  private final AlphaFilter alf = new AlphaFilter();

  private final BufferedImage watermarkInput;
  private final boolean useGrayscaleFilter;

  /**
   * Create the {@link IWatermarkSupplier} by base image of watermark. (Use GrayscaleFilter)
   *
   * @param watermarkInput
   *          the base image to create the watermark
   * @see #ImageWatermarkSupplier(BufferedImage, boolean)
   */
  public ImageWatermarkSupplier(BufferedImage watermarkInput) {
    this(watermarkInput, true);
  }

  /**
   * Create the {@link IWatermarkSupplier} by base image of watermark.
   *
   * @param watermarkInput
   *          the base image to create the watermark
   * @param useGrayscaleFilter
   *          indicates whether a {@link GrayscaleFilter} shall applied upon the input image
   */
  public ImageWatermarkSupplier(BufferedImage watermarkInput, boolean useGrayscaleFilter) {
    this.watermarkInput = watermarkInput;
    this.useGrayscaleFilter = useGrayscaleFilter;
  }

  /**
   * Create the {@link IWatermarkSupplier} by base image of watermark.
   *
   * @param watermarkInput
   *          the base image to create the watermark
   * @param useGrayscaleFilter
   *          indicates whether a {@link GrayscaleFilter} shall applied upon the input image
   */
  public ImageWatermarkSupplier(BufferedImage watermarkInput, boolean useGrayscaleFilter, int threshold) {
    this.watermarkInput = watermarkInput;
    this.useGrayscaleFilter = useGrayscaleFilter;
    this.alf.setThreshold(threshold);
  }
  
  public BufferedImage createWatermark() {
	  return ImageUtils.createARGBImage(this.watermarkInput);
  }
  
  public BufferedImage applyFilters(BufferedImage watermark) {
	  // Apply GrayscaleFilter
      if (this.useGrayscaleFilter) {
        watermark = this.gsf.apply(watermark);
      }
      // Apply ThresholdFilter
      watermark = this.thf.apply(watermark);
      // Set alpha value / create ARGB as we guarantee an ARBG-Image
      watermark = ImageUtils.createARGBImage(watermark);
      watermark = this.alf.apply(watermark);
      return watermark;
  }
}
