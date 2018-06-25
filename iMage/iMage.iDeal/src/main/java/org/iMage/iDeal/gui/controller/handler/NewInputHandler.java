package org.iMage.iDeal.gui.controller.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.gui.util.ImageUtils;
import org.iMage.iDeal.model.IModel;

/**
 * This handler belongs to {@link Events#NEW_INPUT}.
 *
 * @author Dominik Fuchss
 *
 */
public final class NewInputHandler extends Handler {

  private static final int MAX_WIDTH = 1280;
  private static final int MAX_HEIGHT = 1024;

  /**
   * Create handler by MVC.
   *
   * @param model
   *          the model
   * @param view
   *          the view
   * @param controller
   *          the controller
   */
  public NewInputHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    File imageFile = this.view.openImageFileDialog();
    if (imageFile == null) {
      return;
    }
    BufferedImage image = null;
    try {
      image = ImageIO.read(imageFile);
    } catch (IOException e) {
      image = null;
    }
    if (image == null) {
      this.view.showMessage("Error", "Loading not possible or no image", true);
      return;
    }

    if (image.getWidth() > MAX_WIDTH || image.getHeight() > MAX_HEIGHT) {
      boolean yes = this.view.askYesNoQuestion("Image too large",
          "Image is too large .. shall I shrink it?");
      if (!yes) {
        return;
      }
      image = ImageUtils.scaleUntilSmaller(image, MAX_WIDTH, MAX_HEIGHT);
    }

    this.model.setInputImage(imageFile.getName(), image);
  }
}