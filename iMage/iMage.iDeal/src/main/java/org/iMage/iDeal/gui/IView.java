package org.iMage.iDeal.gui;

import java.awt.image.BufferedImage;
import java.io.File;

import org.iMage.iDeal.model.IModel;

/**
 * The view of the MVC in iDeal.
 *
 * @author Dominik Fuchss
 *
 */
public interface IView extends IControlsView, IImagesView {
  /**
   * Get a new {@link IView} by {@link IModel}.
   *
   * @param model
   *          the model
   * @return the new {@link IView}
   */
  static IView by(IModel model) {
    return new MainFrame(model);
  }

  /**
   * Start / Show the view.
   */
  void start();

  /**
   * Show a message to the user.
   * 
   * @param title
   *          the title
   * @param message
   *          the message
   * @param error
   *          indicates whether error or not
   */
  void showMessage(String title, String message, boolean error);

  /**
   * Show an image to the user.
   * 
   * @param title
   *          the tile of the image
   * @param image
   *          the image
   */
  void showImage(String title, BufferedImage image);

  /**
   * Start a FileDialog (open) for Images.
   * 
   * @return the image file or {@code null}
   */
  File openImageFileDialog();

  /**
   * Start a FileDialog (save) for Images.
   * 
   * @return the image file or {@code null}
   */
  File saveImageFileDialog();

  /**
   * Present a Y/N-Question to the user.
   * 
   * @param title
   *          the title
   * @param question
   *          the question
   * @return indicates a <em>yes</em>
   */
  boolean askYesNoQuestion(String title, String question);

}
