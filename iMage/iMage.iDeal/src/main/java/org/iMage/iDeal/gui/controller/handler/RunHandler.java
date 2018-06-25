package org.iMage.iDeal.gui.controller.handler;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.model.IModel;

/**
 * This handler belongs to {@link Events#RUN}.
 *
 * @author Dominik Fuchss
 *
 */
public final class RunHandler extends Handler {
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
  public RunHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    this.model.setWatermarksPerRow(Integer.parseInt(this.view.getWatermarkPerRow()));
    if (!this.model.calculateResult()) {
      this.view.showMessage("Calculation not possible",
          "Please try a smaller number of watermarks per row.", true);
    }
  }

}
