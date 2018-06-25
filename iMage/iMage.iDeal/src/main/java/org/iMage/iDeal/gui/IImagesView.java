/**
 *
 */
package org.iMage.iDeal.gui;

/**
 * The "images" part of the view (Image Panels).
 *
 * @author Dominik Fuchss
 *
 */
public interface IImagesView {
  /**
   * Indicates whether an watermark (base) image is initializable.
   * 
   * @param initializable
   *          indicator
   */
  void setInitializableWM(boolean initializable);

  /**
   * Indicates that an image is savable.
   *
   * @param savable
   *          indicator
   */

  void setSavable(boolean savable);

}
