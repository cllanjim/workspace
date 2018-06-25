package org.iMage.iDeal.gui.components;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;

import org.iMage.iDeal.gui.IControlsView;
import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.gui.util.DocumentActionListener;
import org.iMage.iDeal.gui.util.GBCBuilder;
import org.iMage.iDeal.gui.util.JSliderRestListener;
import org.iMage.iDeal.model.IModel;

/**
 * The ControlsPanel (the bottom element of the view).
 *
 * @author Dominik Fuchss
 *
 */
public class ControlsPanel extends JPanel implements IControlsView {

  private static final long serialVersionUID = 5463109547958306653L;
  private static final String THR_FORMAT = "Threshold (%03d)";
  private JTextField textWMPerRow;
  private JLabel lblThresholdX;
  private JSlider sliderThreshold;
  private JCheckBox cbGrayscale;
  private JButton btnRun;
  private JButton btnSave;

  private Color foregroundColorTextField;

  /**
   * Create the Panel.
   *
   * @param model
   *          the model
   */
  public ControlsPanel(IModel model) {
    GridBagLayout gblControlsPanel = new GridBagLayout();
    gblControlsPanel.columnWidths = new int[] { 0, 0 };
    gblControlsPanel.rowHeights = new int[] { 0, 0, 0, 0 };
    gblControlsPanel.columnWeights = new double[] { 0.0, 1.0 };
    gblControlsPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 1.0 };
    this.setLayout(gblControlsPanel);

    this.createWMPerRow();
    this.createThresholdX();
    this.createGrayscale();
    this.createExecutorsPanel();

    this.createInternalListeners();
    this.initializeValues(model);
  }

  private void createWMPerRow() {
    JLabel lblWMPerRow = new JLabel("WM per row");
    GridBagConstraints gbcLblWMPerRow = new GBCBuilder()//
        .anchor(GridBagConstraints.WEST).build();
    this.add(lblWMPerRow, gbcLblWMPerRow);

    this.textWMPerRow = new JTextField();
    this.textWMPerRow.setColumns(5);
    this.textWMPerRow.setToolTipText("The amount of watermarks per row");

    GridBagConstraints gbcTextWMPerRow = new GBCBuilder()//
        .anchor(GridBagConstraints.WEST).gridx(1).build();
    this.add(this.textWMPerRow, gbcTextWMPerRow);

  }

  private void createThresholdX() {
    this.lblThresholdX = new JLabel();
    GridBagConstraints gbcLblThresholdX = new GBCBuilder()//
        .anchor(GridBagConstraints.WEST).gridy(1).build();
    this.add(this.lblThresholdX, gbcLblThresholdX);

    this.sliderThreshold = new JSlider();
    this.sliderThreshold.setPaintLabels(true);
    this.sliderThreshold.setMajorTickSpacing(25);
    this.sliderThreshold.setPaintTicks(true);
    this.sliderThreshold.setValue(17);
    this.sliderThreshold.setMaximum(255);
    this.sliderThreshold.setToolTipText("The upper threshold for the WatermarkFilter");
    GridBagConstraints gbcSliderThreshold = new GBCBuilder()//
        .fill(GridBagConstraints.HORIZONTAL).gridx(1).gridy(1).build();
    this.add(this.sliderThreshold, gbcSliderThreshold);
  }

  private void createGrayscale() {
    JLabel lblGrayscale = new JLabel("Grayscale");
    GridBagConstraints gbcLblGrayscale = new GBCBuilder()//
        .anchor(GridBagConstraints.WEST).gridy(2).build();
    this.add(lblGrayscale, gbcLblGrayscale);

    this.cbGrayscale = new JCheckBox("", null, true);
    this.cbGrayscale.setToolTipText("Use GrayscaleFilter ?");

    GridBagConstraints gbcCbGrayscale = new GBCBuilder()//
        .anchor(GridBagConstraints.WEST).gridx(1).gridy(2).build();
    this.add(this.cbGrayscale, gbcCbGrayscale);
  }

  private void createExecutorsPanel() {
    JPanel executorsPanel = new JPanel();
    GridBagConstraints gbcExecutorsPanel = new GBCBuilder()//
        .fill(GridBagConstraints.BOTH).gridx(1).gridy(3).build();
    this.add(executorsPanel, gbcExecutorsPanel);
    executorsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
    this.btnRun = new JButton("Run");
    this.btnRun.setToolTipText("Calculate the result image");
    executorsPanel.add(this.btnRun);
    this.btnSave = new JButton("Save");
    this.btnSave.setToolTipText("Save the result image");
    executorsPanel.add(this.btnSave);

  }

  private void initializeValues(IModel model) {
    this.textWMPerRow.setText(Integer.toString(model.getWatermarksPerRow()));
    this.cbGrayscale.setSelected(model.isUseGrayscale());
    this.sliderThreshold.setValue(model.getThreshold());
  }

  /**
   * Register the {@link IController} of the {@link IView}.
   *
   * @param controller
   *          the controller
   */
  public void register(IController controller) {
    JSliderRestListener.by(this.sliderThreshold, e -> controller.handleEvent(Events.PARAM_UPDATE));
    DocumentActionListener.by(this.textWMPerRow.getDocument(),
        e -> controller.handleEvent(Events.PARAM_UPDATE));
    this.cbGrayscale.addActionListener(e -> controller.handleEvent(Events.PARAM_UPDATE));
    this.btnRun.addActionListener(e -> controller.handleEvent(Events.RUN));
    this.btnSave.addActionListener(e -> controller.handleEvent(Events.SAVE));
  }

  private void createInternalListeners() {
    this.sliderThreshold.addChangeListener(e -> this.lblThresholdX
        .setText(String.format(THR_FORMAT, this.sliderThreshold.getValue())));
  }

  @Override
  public void setWMPRValid(boolean valid) {
    if (this.foregroundColorTextField == null) {
      this.foregroundColorTextField = this.textWMPerRow.getForeground();
    }
    if (valid) {
      this.textWMPerRow.setForeground(this.foregroundColorTextField);
    } else {
      this.textWMPerRow.setForeground(Color.RED);
    }
  }

  @Override
  public String getWatermarkPerRow() {
    return this.textWMPerRow.getText();
  }

  @Override
  public boolean isUseGrayscale() {
    return this.cbGrayscale.isSelected();
  }

  @Override
  public int getThreshold() {
    return this.sliderThreshold.getValue();
  }

  @Override
  public void setRunnable(boolean runnable) {
    this.btnRun.setEnabled(runnable);
  }

  @Override
  public void setSavable(boolean savable) {
    this.btnSave.setEnabled(savable);
  }

}
