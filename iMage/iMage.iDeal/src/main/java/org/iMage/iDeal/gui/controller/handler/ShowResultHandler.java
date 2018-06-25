package org.iMage.iDeal.gui.controller.handler;

import java.awt.image.BufferedImage;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.model.IModel;

/**
 * This handler belongs to {@link Events#SHOW_RESULT}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ShowResultHandler extends Handler {

  private static final String TITLE_FORMAT = "%s (threshold %d, WM pr %d, %s)";

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
  public ShowResultHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    BufferedImage result = this.model.getResultImage();
    if (result == null) {
      this.controller.handleEvent(Events.INTERNAL_ERROR);
      return;
    }
    String title = String.format(TITLE_FORMAT, this.model.getInputFilename(),
        this.model.getThreshold(), this.model.getWatermarksPerRow(),
        this.model.isUseGrayscale() ? "grayscale" : "color");
    this.view.showImage(title, result);

  }

}
