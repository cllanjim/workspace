package org.iMage.iDeal.gui.controller.handler;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.model.IModel;

/**
 * This handler belongs to {@link Events#INITIALIZE}.
 * 
 * @author Dominik Fuchss
 *
 */
public final class InitializeHandler extends Handler {
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
  public InitializeHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    this.model.setUseGrayscale(this.view.isUseGrayscale());
    this.model.setThreshold(this.view.getThreshold());

    if (!this.model.calculateWM()) {
      this.view.showMessage("Model Error",
          "The model is not properly initialized. Please consult the Pear Corp. ", true);
    }
  }

}
