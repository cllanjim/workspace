package org.iMage.shutterpile.impl.filters;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile.port.IWatermarkSupplier;

/**
 * This {@link IFilter Filter} adds a watermark ({@link BufferedImage}) to an image.
 *
 * @author Dominic Wolff
 *
 */
public final class WatermarkFilter implements IFilter {

  /**
   * Create a the WatermarkFilter.
   *
   * @param watermark
   *          the watermark image as provided by a {@link IWatermarkSupplier}.
   * @param watermarksPerRow
   *          the number of watermarks in a line (this is meant as desired value. the possible
   *          surplus is drawn)
   */
  public WatermarkFilter(BufferedImage watermark, int watermarksPerRow) {
    // TODO: implement me!
  }

  @Override
  public BufferedImage apply(BufferedImage input) {
    // TODO: implement me!
    return null;
  }

}
