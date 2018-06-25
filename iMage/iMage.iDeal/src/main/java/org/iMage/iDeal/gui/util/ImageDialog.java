/**
 *
 */
package org.iMage.iDeal.gui.util;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 * A simple {@link JDialog} which shows an image.
 *
 * @author Dominik Fuchss
 *
 */
public final class ImageDialog extends JDialog {

  private static final long serialVersionUID = -7890644581009122605L;

  private static final int MAX_WIDTH = 1920;
  private static final int MAX_HEIGHT = 1080;

  private static final int MARGIN_DELTA = 2;

  private ImagePanel imagePanel;

  /**
   * Create {@link ImageDialog} by owner title and image.
   *
   * @param owner
   *          the parent/owner of this dialog
   * @param title
   *          the title of the dialog
   * @param image
   *          the image
   */
  public ImageDialog(Frame owner, String title, BufferedImage image) {
    super(owner);
    this.setSize(MAX_WIDTH, MAX_HEIGHT);
    this.setResizable(false);
    this.setModalityType(ModalityType.APPLICATION_MODAL);
    this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    this.setTitle(title);
    this.setAlwaysOnTop(true);
    this.createContents(image);
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentShown(ComponentEvent e) {
        ImageDialog.this.resize();
      }
    });
  }

  private void createContents(BufferedImage image) {
    GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 0 };
    gridBagLayout.rowHeights = new int[] { 0 };
    gridBagLayout.columnWeights = new double[] { 1.0 };
    gridBagLayout.rowWeights = new double[] { 1.0 };
    this.getContentPane().setLayout(gridBagLayout);

    this.imagePanel = new ImagePanel(image);
    GridBagConstraints cbc = new GridBagConstraints();
    cbc.fill = GridBagConstraints.BOTH;
    this.getContentPane().add(new JScrollPane(this.imagePanel), cbc);
  }

  private void resize() {
    Dimension currentSize = this.getContentPane().getSize();
    int height = this.imagePanel.image.getHeight() - currentSize.height + MARGIN_DELTA;
    int width = this.imagePanel.image.getWidth() - currentSize.width + MARGIN_DELTA;

    width = Math.min(width, 0) + this.getWidth();
    height = Math.min(height, 0) + this.getHeight();
    this.setSize(width, height);

  }

  private static final class ImagePanel extends JPanel {
    private static final long serialVersionUID = 8641059952403890067L;
    private BufferedImage image;

    private ImagePanel(BufferedImage image) {
      this.image = image;
      this.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      g.drawImage(this.image, 0, 0, this);
    }

  }
}
