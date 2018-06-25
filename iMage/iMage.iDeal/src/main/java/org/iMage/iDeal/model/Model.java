package org.iMage.iDeal.model;

import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Set;

import org.iMage.iDeal.util.IObserver;
import org.iMage.shutterpile.impl.Watermarker;
import org.iMage.shutterpile.impl.supplier.ImageWatermarkSupplier;
import org.iMage.shutterpile.port.IWatermarkSupplier;
import org.iMage.shutterpile.port.IWatermarker;

/**
 * The model implementation of iDeal.
 *
 * @author Dominik Fuchss
 *
 */
class Model implements IModel {
  private final Set<IObserver<? super IModel>> observers = new HashSet<>();

  private boolean useGrayscale = true;
  private int threshold = 127;
  private int watermarksPerRow = 3;

  private BufferedImage inputImage;
  private String inputFilename;
  private BufferedImage watermarkBaseImage;
  private IWatermarkSupplier iws;

  private BufferedImage resultImage;

  @Override
  public boolean calculateWM() {
    if (this.watermarkBaseImage == null) {
      return false;
    }
    this.iws = new ImageWatermarkSupplier(this.watermarkBaseImage, this.useGrayscale,
        this.threshold);
    this.informObservers();
    return true;
  }

  @Override
  public boolean calculateResult() {
    if (this.watermarkBaseImage == null || this.inputImage == null) {
      return false;
    }

    IWatermarker wm = new Watermarker(this.iws);
    try {
      this.resultImage = wm.generate(this.inputImage, this.watermarksPerRow);
    } catch (IllegalArgumentException e) {
      this.resultImage = null;
    }
    this.informObservers();
    return this.resultImage != null;
  }

  @Override
  public void registerObserver(IObserver<? super IModel> observer) {
    this.observers.add(observer);
    this.informObservers();
  }

  private void informObservers() {
    for (IObserver<? super IModel> observer : this.observers) {
      observer.invokeUpdate(this);
    }
  }

  @Override
  public boolean isUseGrayscale() {
    return this.useGrayscale;
  }

  @Override
  public int getThreshold() {
    return this.threshold;
  }

  @Override
  public int getWatermarksPerRow() {
    return this.watermarksPerRow;
  }

  @Override
  public BufferedImage getInputImage() {
    return this.inputImage;
  }

  @Override
  public String getInputFilename() {
    return this.inputFilename;
  }

  @Override
  public BufferedImage getResultImage() {
    return this.resultImage;
  }

  @Override
  public BufferedImage getWatermarkImage() {
    if (this.iws == null) {
      return null;
    }
    return this.iws.getWatermark();
  }

  @Override
  public BufferedImage getWatermarkBaseImage() {
    return this.watermarkBaseImage;
  }

  @Override
  public void setUseGrayscale(boolean useGrayscale) {
    if (this.useGrayscale != useGrayscale) {
      this.iws = null;
      this.resultImage = null;
    }
    this.useGrayscale = useGrayscale;
    this.informObservers();
  }

  @Override
  public boolean setThreshold(int threshold) {
    if (threshold < 0 || threshold > 255) {
      return false;
    }

    if (this.threshold != threshold) {
      this.iws = null;
      this.resultImage = null;
    }

    this.threshold = threshold;
    this.informObservers();
    return true;
  }

  @Override
  public boolean setWatermarksPerRow(int watermarksPerRow) {
    if (watermarksPerRow < 1) {
      return false;
    }
    if (this.watermarksPerRow != watermarksPerRow) {
      this.resultImage = null;
    }
    this.watermarksPerRow = watermarksPerRow;
    this.informObservers();
    return true;
  }

  @Override
  public boolean setInputImage(String inputFilename, BufferedImage inputImage) {
    if (inputImage == null || inputFilename == null) {
      return false;
    }
    if (this.inputImage != inputImage) {
      this.resultImage = null;
    }

    this.inputImage = inputImage;
    this.inputFilename = inputFilename;
    this.informObservers();
    return true;
  }

  @Override
  public boolean setWatermarkBaseImage(BufferedImage watermarkImage) {
    if (watermarkImage == null) {
      return false;
    }
    if (this.watermarkBaseImage != watermarkImage) {
      this.iws = null;
      this.resultImage = null;
    }

    this.watermarkBaseImage = watermarkImage;
    this.informObservers();
    return true;
  }

  @Override
  public boolean isWMInitialized() {
    return this.iws != null;
  }

  @Override
  public boolean isInputSet() {
    return this.inputImage != null;
  }

  @Override
  public boolean isWMBaseSet() {
    return this.watermarkBaseImage != null;
  }

  @Override
  public boolean isResultPresent() {
    return this.resultImage != null;
  }

}
