/**
 *
 */
package org.iMage.iDeal.gui.controller.handler;

import java.util.Objects;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.model.IModel;
import org.iMage.iDeal.util.IObserver;

/**
 * This handler belongs to {@link Events#PARAM_UPDATE}.
 *
 * @author Dominik Fuchss
 *
 */
public final class ParameterUpdateHandler extends Handler implements IObserver<IModel> {
  private static final String VALID_WMPR_REGEX = "^[1-9][0-9]*$";

  private boolean initializable;
  private boolean runnable;
  private boolean savable;

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
  public ParameterUpdateHandler(IModel model, IView view, IController controller) {
    super(model, view, controller);
    model.registerObserver(this);
  }

  @Override
  public void run() {
    this.initializable = true;
    this.runnable = true;
    this.savable = true;

    this.checkWMPR();
    this.checkInitializable();
    this.checkRunnable();
    this.checkSavable();

    this.setButtons();
  }

  private void checkInitializable() {
    this.initializable = //
        this.model.isWMBaseSet() && (!this.synced(true) || !this.model.isWMInitialized());
  }

  private void checkWMPR() {
    String wmpr = this.view.getWatermarkPerRow();
    boolean inValid = wmpr == null || !wmpr.matches(VALID_WMPR_REGEX);
    if (inValid) {
      this.runnable = false;
      this.savable = false;
    }
    this.view.setWMPRValid(!inValid);
  }

  private void checkRunnable() {
    if (!this.runnable) {
      return;
    }
    this.runnable = this.model.isWMBaseSet() && this.model.isWMInitialized() && //
        this.synced(true) && this.model.isInputSet();
  }

  private void checkSavable() {
    if (!this.savable) {
      return;
    }
    this.savable = this.runnable && this.synced(false) && this.model.isResultPresent();
  }

  private boolean synced(boolean ignoreWPR) {
    String wmprV = this.view.getWatermarkPerRow();
    String wmprM = Integer.toString(this.model.getWatermarksPerRow());

    boolean gsclV = this.view.isUseGrayscale();
    boolean gsclM = this.model.isUseGrayscale();

    int thrV = this.view.getThreshold();
    int thrM = this.model.getThreshold();

    boolean synced = (ignoreWPR || Objects.equals(wmprV, wmprM)) && gsclV == gsclM && thrV == thrM;
    return synced;
  }

  private void setButtons() {
    this.view.setInitializableWM(this.initializable);
    this.view.setRunnable(this.runnable);
    this.view.setSavable(this.savable);
  }

  @Override
  public void invokeUpdate(IModel model) {
    // ReCheck states in view .. (enabled/disabled etc.)
    this.run();
  }

}
