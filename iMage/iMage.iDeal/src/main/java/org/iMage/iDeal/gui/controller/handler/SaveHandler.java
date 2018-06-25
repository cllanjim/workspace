package org.iMage.iDeal.gui.controller.handler;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.model.IModel;

/**
 * This handler belongs to {@link Events#SAVE}.
 *
 * @author Dominik Fuchss
 *
 */
public final class SaveHandler extends Handler {
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
  public SaveHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    File imageFile = this.view.saveImageFileDialog();
    if (imageFile == null) {
      return;
    }
    BufferedImage image = this.model.getResultImage();
    try {
      ImageIO.write(image, "png", imageFile);
    } catch (IOException e) {
      this.view.showMessage("Save failed", "Save wasn't successful. Check your permissions.", true);
      return;
    }
    this.view.showMessage("Saved Image", "Image file has been saved.", false);
  }
}
