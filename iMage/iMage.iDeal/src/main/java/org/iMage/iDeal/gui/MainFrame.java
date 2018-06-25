package org.iMage.iDeal.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.iMage.iDeal.gui.components.ControlsPanel;
import org.iMage.iDeal.gui.components.ImagesPanel;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.gui.util.GBCBuilder;
import org.iMage.iDeal.gui.util.ImageDialog;
import org.iMage.iDeal.gui.util.ImageFileChooser;
import org.iMage.iDeal.model.IModel;

/**
 * The view implementation of iDeal.
 *
 * @author Dominik Fuchss
 *
 */
final class MainFrame extends JFrame implements IView {

  private static final long serialVersionUID = 2190905544097716983L;
  private JPanel contentPane;
  private ImagesPanel imagesPanel;
  private ControlsPanel controlsPanel;

  private final ImageFileChooser ifc;

  /**
   * Create the main frame.
   *
   * @param model
   *          the model
   */
  MainFrame(IModel model) {
    this.setTitle("iDeal");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setResizable(false);
    this.setBounds(100, 100, 700, 500);
    this.setLocationRelativeTo(null);

    this.ifc = new ImageFileChooser(this);

    this.createContentPane();

    this.createTopSpacePanel();
    this.createImagesPanel(model);
    this.createControlsPanel(model);

    this.createController(model);
  }

  private void createController(IModel model) {
    IController controller = IController.by(model, this);
    this.imagesPanel.register(controller);
    this.controlsPanel.register(controller);
    controller.handleEvent(Events.PARAM_UPDATE);
  }

  private void createContentPane() {
    this.contentPane = new JPanel();
    this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    this.setContentPane(this.contentPane);

    GridBagLayout gblContentPane = new GridBagLayout();
    gblContentPane.columnWidths = new int[] { 0 };
    gblContentPane.rowHeights = new int[] { 0, 0, 0 };
    gblContentPane.columnWeights = new double[] { 1.0 };
    gblContentPane.rowWeights = new double[] { 0.0, 1.0, 0.0 };

    this.contentPane.setLayout(gblContentPane);

  }

  private void createTopSpacePanel() {
    // Just a little bit spacing ..
    JPanel upperSpace = new JPanel();
    GridBagConstraints gbcUpperSpace = new GBCBuilder()//
        .fill(GridBagConstraints.BOTH).build();
    this.contentPane.add(upperSpace, gbcUpperSpace);
  }

  private void createImagesPanel(IModel model) {
    this.imagesPanel = new ImagesPanel(model);
    GridBagConstraints gbcImagesPanel = new GBCBuilder()//
        .fill(GridBagConstraints.BOTH).gridy(1).build();
    this.contentPane.add(this.imagesPanel, gbcImagesPanel);
  }

  private void createControlsPanel(IModel model) {
    this.controlsPanel = new ControlsPanel(model);
    GridBagConstraints gbcControlsPanel = new GBCBuilder()//
        .fill(GridBagConstraints.BOTH).gridy(2).build();
    this.contentPane.add(this.controlsPanel, gbcControlsPanel);

  }

  @Override
  public void start() {
    this.setVisible(true);
  }

  @Override
  public void setWMPRValid(boolean valid) {
    this.controlsPanel.setWMPRValid(valid);
  }

  @Override
  public String getWatermarkPerRow() {
    return this.controlsPanel.getWatermarkPerRow();
  }

  @Override
  public boolean isUseGrayscale() {
    return this.controlsPanel.isUseGrayscale();
  }

  @Override
  public int getThreshold() {
    return this.controlsPanel.getThreshold();
  }

  @Override
  public void showMessage(String title, String message, boolean error) {
    JOptionPane.showMessageDialog(this, message, title,
        error ? JOptionPane.ERROR_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
  }

  @Override
  public void showImage(String title, BufferedImage image) {
    new ImageDialog(this, title, image).setVisible(true);
  }

  @Override
  public File openImageFileDialog() {
    return this.ifc.openFile();
  }

  @Override
  public File saveImageFileDialog() {
    return this.ifc.saveFile();
  }

  @Override
  public void setRunnable(boolean runnable) {
    this.controlsPanel.setRunnable(runnable);
  }

  @Override
  public void setSavable(boolean savable) {
    this.controlsPanel.setSavable(savable);
    this.imagesPanel.setSavable(savable);
  }

  @Override
  public void setInitializableWM(boolean initializable) {
    this.imagesPanel.setInitializableWM(initializable);
  }

  @Override
  public boolean askYesNoQuestion(String title, String question) {
    int result = JOptionPane.showConfirmDialog(this, question, title, JOptionPane.YES_NO_OPTION);
    return result == JOptionPane.YES_OPTION;
  }

}
