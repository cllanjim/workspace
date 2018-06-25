package org.iMage.iDeal;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.model.IModel;

/**
 * The main class of iDeal.
 *
 * @author Dominik Fuchss
 *
 */
public final class Main {
  private Main() {
    throw new IllegalAccessError();
  }

  /**
   * The main method of iDeal.
   *
   * @param args
   *          the command line arguments
   */
  public static void main(String[] args) {
    setSystemLookAndFeel();
    IModel model = IModel.by();
    SwingUtilities.invokeLater(IView.by(model)::start);
  }

  private static void setSystemLookAndFeel() {
    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
        | UnsupportedLookAndFeelException e) {
      System.err.println("Cannot set system's look and feel ..");
    }
  }

}
