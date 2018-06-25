package org.iMage.iDeal.model;

import java.awt.image.BufferedImage;

import org.iMage.iDeal.util.IObserver;
import org.iMage.shutterpile.impl.filters.GrayscaleFilter;
import org.iMage.shutterpile.impl.filters.ThresholdFilter;

/**
 * The model of the MVC in iDeal.
 *
 * @author Dominik Fuchss
 *
 */
public interface IModel {
  /**
   * Get a new {@link IModel} of iDeal.
   *
   * @return a new {@link IModel}
   */
  static IModel by() {
    return new Model();
  }

  /**
   * Register an {@link IObserver}.
   *
   * @param observer
   *          the observer
   */
  void registerObserver(IObserver<? super IModel> observer);

  /**
   * Set the amount of watermarks per row.
   *
   * @param watermarksPerRow
   *          the watermarks per row
   * @return {@code true} if successful, {@code false} otherwise
   */
  boolean setWatermarksPerRow(int watermarksPerRow);

  /**
   * Get the amount of watemarks per row.
   *
   * @return the watermarks per row
   */
  int getWatermarksPerRow();

  /**
   * Set the threshold for the {@link ThresholdFilter}.
   *
   * @param threshold
   *          the threshold
   * @return {@code true} if successful, {@code false} otherwise
   */
  boolean setThreshold(int threshold);

  /**
   * Get the current threshold of the {@link ThresholdFilter}.
   *
   * @return the current threshold
   */
  int getThreshold();

  /**
   * Activate {@link GrayscaleFilter} for the watermark generation.
   *
   * @param useGrayscale
   *          indicates the use of the filter
   */
  void setUseGrayscale(boolean useGrayscale);

  /**
   * Indicates the use of the {@link GrayscaleFilter} for watermark generation.
   *
   * @return indicator for the {@link GrayscaleFilter}
   */
  boolean isUseGrayscale();

  /**
   * Set the base image for watermarking.
   *
   * @param watermarkImage
   *          the base image
   * @return {@code true} if successful, {@code false} otherwise
   *
   */
  boolean setWatermarkBaseImage(BufferedImage watermarkImage);

  /**
   * Get the watermark base image.
   *
   * @return the watermark base image
   */
  BufferedImage getWatermarkBaseImage();

  /**
   * Get the calculated watermark image.
   *
   * @return the watermark image
   */
  BufferedImage getWatermarkImage();

  /**
   * Get the current input image.
   *
   * @return the input image
   */
  BufferedImage getInputImage();

  /**
   * Set the current input image.
   *
   * @param filename
   *          the filename of the image
   * @param inputImage
   *          the image itself
   * @return {@code true} if successful, {@code false} otherwise
   *
   */
  boolean setInputImage(String filename, BufferedImage inputImage);

  /**
   * Get the name of the input image (filename).
   *
   * @return the name
   */
  String getInputFilename();

  /**
   * Get the calculated result image.
   *
   * @return the result image
   */
  BufferedImage getResultImage();

  /**
   * Indicates whether the input image is set.
   *
   * @return the indicator
   */
  boolean isInputSet();

  /**
   * Indicates whether the watermark base image is set.
   *
   * @return the indicator
   */
  boolean isWMBaseSet();

  /**
   * Indicates whether the watermark image is calculated.
   *
   * @return the indicator
   */
  boolean isWMInitialized();

  /**
   * Indicates whether the result image is present.
   *
   * @return the indicator
   */
  boolean isResultPresent();

  /**
   * Calculates the watermark image (from base watermark image).
   *
   * @return {@code true} if successful, {@code false} otherwise
   */
  boolean calculateWM();

  /**
   * Calculates the result image.
   *
   * @return {@code true} if successful, {@code false} otherwise
   */
  boolean calculateResult();

}
