/**
 *
 */
package org.iMage.iDeal.gui.controller.handler;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.model.IModel;

/**
 * The base class of all handlers of {@link Events}
 *
 * @author Dominik Fuchss
 *
 */
public abstract class Handler {

  protected final IModel model;
  protected final IView view;
  protected final IController controller;

  /**
   * Set MVC parts in Handler.
   * 
   * @param model
   *          the model
   * @param view
   *          the view
   * @param controller
   *          the controller
   */
  protected Handler(IModel model, IView view, IController controller) {
    this.model = model;
    this.view = view;
    this.controller = controller;
  }

  /**
   * Run/Execute the handler.
   */
  public abstract void run();

}
