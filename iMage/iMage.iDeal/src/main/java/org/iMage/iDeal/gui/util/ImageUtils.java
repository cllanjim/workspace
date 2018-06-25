package org.iMage.iDeal.gui.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.iMage.shutterpile.impl.util.ARGBConverter;

/**
 * This class contains some useful methods to work with {@link BufferedImage BufferedImages}.
 *
 * @author Dominik Fuchss
 * @version 2
 */
public final class ImageUtils {

  private static final double SCALE_FACTOR = 1 / Math.sqrt(2);

  private ImageUtils() {
    throw new IllegalAccessError();
  }

  /**
   * Create an ARGB {@link BufferedImage} by the original input image.
   *
   * @param input
   *          the input image
   * @return the converted (new) image
   */
  public static BufferedImage createARGBImage(BufferedImage input) {
    BufferedImage res = null;
    if (input.getType() != BufferedImage.TYPE_INT_ARGB) {
      return ARGBConverter.convert(input);
    } else {
      ColorModel cm = input.getColorModel();
      boolean isAlphaPremultiplied = cm.isAlphaPremultiplied();
      res = new BufferedImage(cm, input.copyData(null), isAlphaPremultiplied, null);
    }
    return res;
  }

  /**
   * Scale an image until size in range (halve the size until small enough).
   *
   * @param input
   *          the image
   * @param maxWidth
   *          the maximum width
   * @param maxHeight
   *          the maximum height
   * @return the shrinked image or {@code null} if too small
   */
  public static BufferedImage scaleUntilSmaller(BufferedImage input, int maxWidth, int maxHeight) {
    BufferedImage image = input;
    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    while (imageWidth > maxWidth || imageHeight > maxHeight) {
      int targetWidth = (int) (SCALE_FACTOR * imageWidth);
      int targetHeight = (int) (SCALE_FACTOR * imageHeight);
      if (targetWidth <= 0 || targetHeight <= 0) {
        return null;
      }
      if (imageWidth > imageHeight) {
        // Landscape
        image = scaleWidth(image, targetWidth);
      } else {
        // Portrait
        image = scaleHeight(image, targetHeight);
      }
      imageWidth = image.getWidth();
      imageHeight = image.getHeight();

    }
    return image;
  }

  /**
   * Scale Image by width (set width and the height will calculated).
   *
   * @param input
   *          image to scale
   *
   * @param width
   *          the target width
   *
   * @return scaled image
   */
  public static BufferedImage scaleWidth(BufferedImage input, int width) {
    if (width <= 0) {
      throw new IllegalArgumentException("width cannot be <= 0");
    }
    Image scaled = input.getScaledInstance(width, -1, Image.SCALE_SMOOTH);
    int height = scaled.getHeight(null);
    if (height <= 0) {
      throw new IllegalArgumentException("height would be 0");
    }
    BufferedImage res = new BufferedImage(width, height, input.getType());
    Graphics2D g2d = res.createGraphics();
    g2d.drawImage(scaled, 0, 0, null);
    g2d.dispose();
    res.flush();
    return res;
  }

  /**
   * Scale Image by width (set height and the width will calculated).
   *
   * @param input
   *          image to scale
   *
   * @param height
   *          the target width
   *
   * @return scaled image
   */
  public static BufferedImage scaleHeight(BufferedImage input, int height) {
    if (height <= 0) {
      throw new IllegalArgumentException("width cannot be <= 0");
    }
    Image scaled = input.getScaledInstance(-1, height, Image.SCALE_SMOOTH);
    int width = scaled.getWidth(null);
    if (width <= 0) {
      throw new IllegalArgumentException("width would be 0");
    }
    BufferedImage res = new BufferedImage(width, height, input.getType());
    Graphics2D g2d = res.createGraphics();
    g2d.drawImage(scaled, 0, 0, null);
    g2d.dispose();
    res.flush();
    return res;
  }

  /**
   * Create an Icon by an Image (path of it).
   *
   * @param imagePath
   *          the image path
   * @param targetWidth
   *          the icon width
   * @param targetHeight
   *          the icon height
   * @param background
   *          the background color of the icon
   * @param padding
   *          the padding (xy in pixels) of the icon
   * @return the icon
   */
  public static Icon getIcon(String imagePath, int targetWidth, int targetHeight, Color background,
      int padding) {
    BufferedImage image = null;
    try {
      image = ImageIO.read(ImageUtils.class.getResourceAsStream(imagePath));
    } catch (IOException e) {
      return null;
    }
    return getIcon(image, targetWidth, targetHeight, background, padding);
  }

  /**
   * Create an Icon by an Image.
   *
   * @param input
   *          the image
   * @param targetWidth
   *          the icon width
   * @param targetHeight
   *          the icon height
   * @param background
   *          the background color of the icon
   * @param padding
   *          the padding (xy in pixels) of the icon
   * @return the icon
   */
  public static Icon getIcon(BufferedImage input, int targetWidth, int targetHeight,
      Color background, int padding) {
    BufferedImage image = input;
    int imageWidth = image.getWidth();
    int imageHeight = image.getHeight();

    if (imageWidth > imageHeight) {
      // Landscape
      image = scaleWidth(image, targetWidth - 2 * padding);
    } else {
      // Portrait
      image = scaleHeight(image, targetHeight - 2 * padding);
    }

    BufferedImage result = new BufferedImage(targetWidth, targetHeight,
        BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2d = result.createGraphics();
    g2d.setColor(background);
    g2d.fillRect(0, 0, targetWidth, targetHeight);
    int x = (targetWidth - 2 * padding - image.getWidth()) / 2 + padding;
    int y = (targetHeight - 2 * padding - image.getHeight()) / 2 + padding;
    g2d.drawImage(image, x, y, null);
    g2d.dispose();
    result.flush();

    return new ImageIcon(result);
  }

}
