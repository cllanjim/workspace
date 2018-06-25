package org.iMage.iDeal.gui.controller;

import java.util.HashMap;
import java.util.Map;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.handler.Handler;
import org.iMage.iDeal.gui.controller.handler.InitializeHandler;
import org.iMage.iDeal.gui.controller.handler.InternalErrorHandler;
import org.iMage.iDeal.gui.controller.handler.NewInputHandler;
import org.iMage.iDeal.gui.controller.handler.NewWatermarkHandler;
import org.iMage.iDeal.gui.controller.handler.ParameterUpdateHandler;
import org.iMage.iDeal.gui.controller.handler.RunHandler;
import org.iMage.iDeal.gui.controller.handler.SaveHandler;
import org.iMage.iDeal.gui.controller.handler.ShowResultHandler;
import org.iMage.iDeal.model.IModel;

/**
 * The controller implementation of iDeal.
 *
 * @author Dominik Fuchss
 *
 */
final class Controller implements IController {
  private final IModel model;
  private final IView view;

  private final Map<Events, Handler> handlers;
  private final Handler internalError;

  /**
   * Create a controller by model and view.
   *
   * @param model
   *          the model
   * @param view
   *          the view
   */
  Controller(IModel model, IView view) {
    this.model = model;
    this.view = view;
    this.handlers = new HashMap<>();
    this.internalError = new InternalErrorHandler(model, view, this);
    this.initHandlers();
  }

  private void initHandlers() {
    this.handlers.put(Events.INTERNAL_ERROR, this.internalError);
    this.handlers.put(Events.PARAM_UPDATE, new ParameterUpdateHandler(this.model, this.view, this));
    this.handlers.put(Events.NEW_INPUT, new NewInputHandler(this.model, this.view, this));
    this.handlers.put(Events.NEW_WATERMARK, new NewWatermarkHandler(this.model, this.view, this));
    this.handlers.put(Events.INITIALIZE, new InitializeHandler(this.model, this.view, this));
    this.handlers.put(Events.SHOW_RESULT, new ShowResultHandler(this.model, this.view, this));
    this.handlers.put(Events.RUN, new RunHandler(this.model, this.view, this));
    this.handlers.put(Events.SAVE, new SaveHandler(this.model, this.view, this));
  }

  @Override
  public void handleEvent(Events event) {
    if (event == null) {
      return;
    }
    Handler handler = this.handlers.getOrDefault(event, this.internalError);
    //    System.err.println("Execute: " + handler.getClass().getSimpleName());
    handler.run();
  }

}
