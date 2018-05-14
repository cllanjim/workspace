package org.iMage.shutterpile.impl.supplier;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.port.IWatermarkSupplier;

/**
 * This class realizes a {@link IWatermarkSupplier} which uses a {@link BufferedImage} to generate
 * the watermark.
 *
 * @author Dominic Wolff
 *
 */
public final class ImageWatermarkSupplier implements IWatermarkSupplier {

  /**
   * Create the {@link IWatermarkSupplier} by base image of watermark.
   *
   * @param watermarkInput
   *          the base image to create the watermark
   */
  public ImageWatermarkSupplier(BufferedImage watermarkInput) {
    // TODO: implement me!

  }

  @Override
  public BufferedImage getWatermark() {
    // TODO: implement me!
    return null;
  }

}
