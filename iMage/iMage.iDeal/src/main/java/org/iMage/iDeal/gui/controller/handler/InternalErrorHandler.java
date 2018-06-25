/**
 *
 */
package org.iMage.iDeal.gui.controller.handler;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.model.IModel;

/**
 * This handler belongs to {@link Events#INTERNAL_ERROR}.
 *
 * @author Dominik Fuchss
 *
 */
public final class InternalErrorHandler extends Handler {
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
  public InternalErrorHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
  }

  @Override
  public void run() {
    this.view.showMessage("Internal Error",
        "Some internal error occured. Please consult the Pear Corp.", true);
  }

}
