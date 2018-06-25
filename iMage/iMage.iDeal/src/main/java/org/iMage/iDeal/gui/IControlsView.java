/**
 *
 */
package org.iMage.iDeal.gui;

import org.iMage.shutterpile.impl.Watermarker;
import org.iMage.shutterpile.impl.filters.GrayscaleFilter;

/**
 * The "controls" part of the view (User Controls).
 *
 * @author Dominik Fuchss
 *
 */
public interface IControlsView {
  /**
   * Indicates a valid watermark per row value.
   *
   * @param valid
   *          indicator
   */
  void setWMPRValid(boolean valid);

  /**
   * Indicates that a {@link Watermarker} is ready.
   *
   * @param runnable
   *          indicator
   */
  void setRunnable(boolean runnable);

  /**
   * Indicates that an image is savable.
   *
   * @param savable
   *          indicator
   */
  void setSavable(boolean savable);

  /**
   * Get the watermarks per row specified by the user.
   *
   * @return the watermarks per row
   */
  String getWatermarkPerRow();

  /**
   * Get the indicator for the use of the {@link GrayscaleFilter} as defined by the user.
   *
   * @return indicator
   */
  boolean isUseGrayscale();

  /**
   * Get the threshold value as defined by the user.
   *
   * @return the threshold value
   */
  int getThreshold();

}
