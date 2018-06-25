package org.iMage.iDeal.gui.util;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * A simple FileChooser for image files.
 *
 * @author Dominik Fuchss
 *
 */
public final class ImageFileChooser {
  private static final String[] VALID_EXTENSIONS = new String[] { "png" };

  private final JFileChooser jfc;

  private final Component parent;

  /**
   * Create {@link ImageFileChooser} by parent
   *
   * @param parent
   *          the parent
   */
  public ImageFileChooser(Component parent) {
    this.parent = parent;
    FileNameExtensionFilter filter = new FileNameExtensionFilter("Image Files", VALID_EXTENSIONS);
    this.jfc = new JFileChooser();
    this.jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
    this.jfc.setAcceptAllFileFilterUsed(false);
    this.jfc.setFileFilter(filter);

  }

  /**
   * Open file dialog.
   *
   * @return the file or {@code null}
   */
  public File openFile() {
    int result = this.jfc.showOpenDialog(this.parent);
    File selected = this.jfc.getSelectedFile();
    if (result == JFileChooser.APPROVE_OPTION && selected != null && selected.exists()) {
      return selected;
    }
    return null;
  }

  /**
   * Save file dialog.
   *
   * @return the file or {@code null}
   */
  public File saveFile() {
    int result = this.jfc.showSaveDialog(this.parent);
    File selected = this.jfc.getSelectedFile();
    if (result == JFileChooser.APPROVE_OPTION && selected != null) {
      return selected;
    }
    return null;

  }

}
