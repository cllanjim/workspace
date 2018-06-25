package org.iMage.iDeal.gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.iMage.iDeal.gui.IImagesView;
import org.iMage.iDeal.gui.IView;
import org.iMage.iDeal.gui.controller.Events;
import org.iMage.iDeal.gui.controller.IController;
import org.iMage.iDeal.gui.util.GBCBuilder;
import org.iMage.iDeal.gui.util.ImageUtils;
import org.iMage.iDeal.model.IModel;
import org.iMage.iDeal.util.IObserver;

/**
 * The ImagesPanel (the top element of the view).
 *
 * @author Dominik Fuchss
 *
 */
public class ImagesPanel extends JPanel implements IObserver<IModel>, IImagesView {

  private static final long serialVersionUID = 4531383487062055759L;
  private static final Color BTN_BG = new Color(0, 0, 0, 0);
  private static final int BTN_PAD = 5;

  private JButton btnOriginal;
  private JButton btnWatermark;
  private JButton btnWatermarked;
  private JButton btnInit;

  /**
   * Create the Panel.
   *
   * @param model
   *          the model
   */
  public ImagesPanel(IModel model) {
    GridBagLayout gblImagesPanel = new GridBagLayout();
    gblImagesPanel.columnWidths = new int[] { 200, 100, 200 };
    gblImagesPanel.columnWeights = new double[] { 1.0, 1.0, 1.0 };
    gblImagesPanel.rowWeights = new double[] { 1.0 };
    this.setLayout(gblImagesPanel);

    this.createOriginalPanel();
    this.createWatermarkPanel();
    this.createResultPanel();

    model.registerObserver(this);
  }

  private void createOriginalPanel() {
    JPanel originalPanel = new JPanel();
    GridBagConstraints gbcOriginalPanel = new GBCBuilder()//
        .anchor(GridBagConstraints.WEST).fill(GridBagConstraints.VERTICAL).build();
    this.add(originalPanel, gbcOriginalPanel);

    GridBagLayout gblOriginalPanel = new GridBagLayout();
    gblOriginalPanel.columnWidths = new int[] { 0 };
    gblOriginalPanel.rowHeights = new int[] { 0, 0 };
    gblOriginalPanel.columnWeights = new double[] { 1.0 };
    gblOriginalPanel.rowWeights = new double[] { 0.0, 1.0 };
    originalPanel.setLayout(gblOriginalPanel);

    JLabel lblOriginal = new JLabel("Original");
    lblOriginal.setFont(new Font("Castellar", Font.PLAIN, 22));
    GridBagConstraints gbcLblOriginal = new GBCBuilder()//
        .anchor(GridBagConstraints.NORTH).build();
    originalPanel.add(lblOriginal, gbcLblOriginal);

    this.btnOriginal = new JButton();
    this.btnOriginal.setPreferredSize(new Dimension(200, 150));
    this.btnOriginal.setFocusable(false);
    this.btnOriginal.setToolTipText("Load an input image to apply watermarks");

    GridBagConstraints gbcBtnOriginal = new GBCBuilder()//
        .gridy(1).build();
    originalPanel.add(this.btnOriginal, gbcBtnOriginal);

  }

  private void createWatermarkPanel() {
    JPanel watermarkPanel = new JPanel();
    GridBagConstraints gbcWatermarkPanel = new GBCBuilder()//
        .fill(GridBagConstraints.VERTICAL).gridx(1).build();
    this.add(watermarkPanel, gbcWatermarkPanel);

    GridBagLayout gblWatermarkPanel = new GridBagLayout();
    gblWatermarkPanel.columnWidths = new int[] { 0 };
    gblWatermarkPanel.rowHeights = new int[] { 0, 0, 0, 0 };
    gblWatermarkPanel.columnWeights = new double[] { 1.0 };
    gblWatermarkPanel.rowWeights = new double[] { 0.0, 1.0, 0.0, 1.0 };
    watermarkPanel.setLayout(gblWatermarkPanel);

    JLabel lblWatermark = new JLabel("Watermark");
    lblWatermark.setFont(new Font("Castellar", Font.PLAIN, 22));
    GridBagConstraints gbcLblWatermark = new GBCBuilder()//
        .anchor(GridBagConstraints.NORTH).build();
    watermarkPanel.add(lblWatermark, gbcLblWatermark);

    this.btnWatermark = new JButton();
    this.btnWatermark.setPreferredSize(new Dimension(100, 100));
    this.btnWatermark.setFocusable(false);
    this.btnWatermark.setToolTipText("Load an image as watermark");

    GridBagConstraints gbcBtnWatermark = new GBCBuilder()//
        .anchor(GridBagConstraints.SOUTH).gridy(1).build();
    watermarkPanel.add(this.btnWatermark, gbcBtnWatermark);

    this.btnInit = new JButton("Init");
    this.btnInit.setPreferredSize(new Dimension(100, 25));
    this.btnInit.setFocusable(false);
    this.btnInit.setToolTipText("Apply filters to watermark image");

    GridBagConstraints gbcBtnInit = new GBCBuilder()//
        .fill(GridBagConstraints.VERTICAL).gridy(2).build();
    watermarkPanel.add(this.btnInit, gbcBtnInit);

  }

  private void createResultPanel() {
    JPanel resultPanel = new JPanel();
    GridBagConstraints gbcResultPanel = new GBCBuilder()//
        .anchor(GridBagConstraints.EAST).fill(GridBagConstraints.VERTICAL).gridx(2).build();
    this.add(resultPanel, gbcResultPanel);

    GridBagLayout gblResultPanel = new GridBagLayout();
    gblResultPanel.columnWidths = new int[] { 0 };
    gblResultPanel.rowHeights = new int[] { 0, 0 };
    gblResultPanel.columnWeights = new double[] { 1.0 };
    gblResultPanel.rowWeights = new double[] { 0.0, 1.0 };
    resultPanel.setLayout(gblResultPanel);

    JLabel lblWatermarked = new JLabel("Watermarked");
    lblWatermarked.setFont(new Font("Castellar", Font.PLAIN, 22));
    GridBagConstraints gbcLblWatermarked = new GBCBuilder()//
        .build();
    resultPanel.add(lblWatermarked, gbcLblWatermarked);

    this.btnWatermarked = new JButton();
    this.btnWatermarked.setPreferredSize(new Dimension(200, 150));
    this.btnWatermarked.setFocusable(false);
    this.btnWatermarked.setToolTipText("Show result in window");
    GridBagConstraints gbcBtnWatermarked = new GBCBuilder()//
        .gridy(1).build();
    resultPanel.add(this.btnWatermarked, gbcBtnWatermarked);

  }

  @Override
  public void invokeUpdate(IModel model) {
    BufferedImage watermark = model.isWMInitialized() //
        ? model.getWatermarkImage()
        : model.getWatermarkBaseImage();
    this.setButtonIcon(this.btnOriginal, model.getInputImage(), "/images/input.png");
    this.setButtonIcon(this.btnWatermark, watermark, "/images/watermark.png");
    this.setButtonIcon(this.btnWatermarked, model.getResultImage(), "/images/result.png");
  }

  private void setButtonIcon(JButton button, BufferedImage image, String fallbackURL) {
    Dimension target = button.getPreferredSize();
    Icon icon = null;
    if (image == null) {
      icon = ImageUtils.getIcon(fallbackURL, target.width, target.height, BTN_BG, BTN_PAD);
    } else {
      icon = ImageUtils.getIcon(image, target.width, target.height, BTN_BG, BTN_PAD);
    }

    if (icon != null) {
      button.setIcon(icon);
    }

  }

  /**
   * Register the {@link IController} of the {@link IView}.
   *
   * @param controller
   *          the controller
   */
  public void register(IController controller) {
    this.btnOriginal.addActionListener(e -> controller.handleEvent(Events.NEW_INPUT));
    this.btnWatermark.addActionListener(e -> controller.handleEvent(Events.NEW_WATERMARK));
    this.btnWatermarked.addActionListener(e -> controller.handleEvent(Events.SHOW_RESULT));
    this.btnInit.addActionListener(e -> controller.handleEvent(Events.INITIALIZE));
  }

  @Override
  public void setInitializableWM(boolean initializable) {
    this.btnInit.setEnabled(initializable);
  }

  @Override
  public void setSavable(boolean savable) {
    this.btnWatermarked.setEnabled(savable);
  }
}
