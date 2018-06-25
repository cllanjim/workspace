/**
 *
 */
package org.iMage.iDeal.gui.controller;

/**
 * This enumeration defines all Events for the {@link IController}.
 *
 * @author Dominik Fuchss
 *
 */
public enum Events {
  /**
   * Calculate watermark by watermark base image.
   */
  INITIALIZE,
  /**
   * Calculate the result image.
   */
  RUN,
  /**
   * Save the result image.
   */
  SAVE,

  /**
   * Load new watermark base file.
   */
  NEW_WATERMARK,
  /**
   * Load new input file.
   */
  NEW_INPUT,
  /**
   * Parameters have been changed in view.
   */
  PARAM_UPDATE,
  /**
   * Show result image.
   */
  SHOW_RESULT,
  /**
   * Internal error event.
   */
  INTERNAL_ERROR
}
