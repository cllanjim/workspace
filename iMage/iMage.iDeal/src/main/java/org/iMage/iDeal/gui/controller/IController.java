package org.iMage.iDeal.gui.controller;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.model.IModel;

/**
 * The controller of the MVC in iDeal.
 *
 * @author Dominik Fuchss
 *
 */
public interface IController {
  /**
   * Create a new {@link IController} by {@link IModel} and {@link IView}.
   * 
   * @param model
   *          the model
   * @param view
   *          the view
   * @return the new {@link IController}
   */
  static IController by(IModel model, IView view) {
    return new Controller(model, view);
  }

  /**
   * Handle an event.
   * 
   * @param event
   *          the event
   */
  void handleEvent(Events event);

}
