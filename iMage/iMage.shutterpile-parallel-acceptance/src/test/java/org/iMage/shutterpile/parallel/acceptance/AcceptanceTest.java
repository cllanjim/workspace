package org.iMage.shutterpile.parallel.acceptance;

import static org.junit.Assert.fail;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;

import org.iMage.shutterpile.impl.supplier.ImageWatermarkSupplier;
import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile.port.IWatermarkSupplier;
import org.iMage.shutterpile_parallel.impl.filters.ParallelWatermarkFilter;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Acceptance test for the {@link ParallelWatermarkFilter}.
 * @author lgavr
 *
 */
public class AcceptanceTest extends TestBase {

	private static String[] inputImagePaths = 
		{"/F10_Eingabe.png", "/F10_WZ.png", "/F10_Ausgabe.png", "/NF10_WZ.png", "/NF10_Bild.png"};
	private static BufferedImage[] testImages;

	/**
	 * Prepare the tests: Load the test-images.
	 */
	@BeforeClass
	public static void loadImages() {
		testImages = new BufferedImage[inputImagePaths.length];
		for (int i = 0; i < inputImagePaths.length; i++) {
			testImages[i] = AcceptanceTest.loadImage(inputImagePaths[i], "png");
		}
		IWatermarkSupplier wmSupplier = new ImageWatermarkSupplier(testImages[1]);
		testImages[1] = wmSupplier.getWatermark();
		wmSupplier = new ImageWatermarkSupplier(testImages[3]);
		testImages[3] = wmSupplier.getWatermark();
	}
	
	/**
	 * F10
	 * 
	 * Die Ausführung des sequentiellen Wasserzeichenfilters erzeugt für das Bild 
	 * "F10_Eingabe.png", das Wasserzeichen-Bild "F10_WZ.png" und 2 Wasserzeichen 
	 * pro Reihe immer das Bild "F10_Ausgabe.png".
	 */
	@Test
	public void twoWatermarksPerRow() {
		IFilter seqWMF = new ParallelWatermarkFilter(testImages[1], 2, 1);
		BufferedImage result = seqWMF.apply(testImages[0]);
		AcceptanceTest.compareImages(testImages[2], result, false);
	}

	/**
	 * F20
	 * 
	 * Die Ausführung des parallelen Wasserzeichenfilters – egal ob mit 2, 4 oder 8 Fäden – 
	 * erzeugt immer das gleiche Ergebnis wie die sequentielle Variante des Filters.
	 */
	@Test
	public void sameResultForDifferentAmountOfThreads() {
		IFilter pwfONE = new ParallelWatermarkFilter(testImages[1], 10, 1);
		BufferedImage reference = pwfONE.apply(testImages[0]);
		for (int i = 1; i <= 3; i++) {
			IFilter pwfTest = new ParallelWatermarkFilter(testImages[1], 10, (int) Math.pow(2, i));
			BufferedImage result = pwfTest.apply(testImages[0]);
			AcceptanceTest.compareImages(reference, result, false);
		}
	}

	/**
	 * NF10
	 * 
	 * Die Ausführzeit des sequentiellen Wasserzeichenfilters für das Bild NF10_Bild.PNG, 
	 * das Wasserzeichen-Bild NF10_WZ.PNG und 30 Wasserzeichen pro Reihe beträgt höchstens 1,5 Sekunden.
	 */
	@Ignore @Test
	public void durationTimeLimit() {
		IFilter seqPWF = new ParallelWatermarkFilter(testImages[3], 30, 1);
		double seqDuration = AcceptanceTest.measureTime(seqPWF, testImages[4]);
		Assert.assertTrue("Application took longer than 1,5 seconds. (Was " + seqDuration / 1000 + "seconds)", 
				seqDuration <= 1500);
	}
	
	/**
	 * NF20 
	 * 
	 * Die Laufzeit des parallelen Wasserzeichenfilters mit zwei Ausführungsfäden für das 
	 * Bild NF10_Bild.PNG, das Wasserzeichen-Bild NF10_WZ.PNG und 30 Wasserzeichen 
	 * pro Reihe beträgt höchstens 75% der Laufzeit der sequentiellen Ausführung des Filters.
	 */
	@Test
	public void parallelIsMoreTimeEffizient() {
		IFilter seqPWF = new ParallelWatermarkFilter(testImages[3], 30, 1);
		double seqDuration = AcceptanceTest.measureTime(seqPWF, testImages[4]);
		
		IFilter pwfTWO = new ParallelWatermarkFilter(testImages[3], 30, 2);
		double parDuration = AcceptanceTest.measureTime(pwfTWO, testImages[4]);
		
		double factor = parDuration / seqDuration;
		Assert.assertTrue(
				"Parallel version doesn't take less than 75% of the time, than sequential version. "
				+ "(It took around " + (int) (factor * 100) + "% the time of the sequential version)",
				factor <= 0.75);
	}

	/**
	 * NF30
	 * 
	 * Die durchschnittliche Laufzeit bei 10 Durchläufen des parallelen Wasserzeichenfilters 
	 * mit zwei Ausführungsfäden für das Bild NF10_Bild.PNG, das Wasserzeichen-Bild 
	 * NF10_WZ.PNG und 30 Wasserzeichen pro Reihe beträgt höchstens 1 Sekunde.
	 */
	@Test
	public void averageDurationTimeIsUnderOneSecond() {
		int iterations = 10;
		double averageDuration = 0;
		for (int i = 0; i < iterations; i++) {
			IFilter pwf = new ParallelWatermarkFilter(testImages[3], 30, 2);
			double thisDuration = AcceptanceTest.measureTime(pwf, testImages[4]);
			double factored = thisDuration / iterations;
			averageDuration += factored;
		}
		Assert.assertTrue("Parallel version took longer than 1 second on average. "
				+ "(It took " + averageDuration / 1000 + " seconds)",
				averageDuration <= 1000);
	}

	/**
	 * NF40
	 * 
	 * Die maximale Abweichung bei 10 Durchläufen des parallelen Wasserzeichenfilters mit 
	 * zwei Ausführungsfäden für das Bild NF10_Bild.PNG, das Wasserzeichen-Bild 
	 * NF10_WZ.PNG und 30 Wasserzeichen pro Reihe beträgt pro Lauf höchstens 100 Millisekunden.
	 */
	@Ignore @Test
	public void maximalVariationLimit() {
		int iterations = 10;
		double maxDuration = 0;
		double minDuration = Double.MAX_VALUE;
		for (int i = 0; i < iterations; i++) {
			IFilter pwf = new ParallelWatermarkFilter(testImages[3], 30, 2);
			double thisDuration = AcceptanceTest.measureTime(pwf, testImages[4]);
			if (thisDuration >= maxDuration) {
				maxDuration = thisDuration;
			}
			if (thisDuration <= minDuration) {
				minDuration = thisDuration;
			}
		}
		double delta = maxDuration - minDuration;
		Assert.assertTrue("Variation of duration was too great. (It was " + delta + "ms)",
				delta <= 100);
	}
	
	/**
	 * Loads an image-file into a BufferedImage.
	 * @param file
	 * 			The file to load.
	 * @param format
	 * 			The format to load the file in.
	 * @return
	 * 			The loaded BufferedImage.
	 */
	private static BufferedImage loadImage(String file, String format) {
	    try {
	    	InputStream stream = AcceptanceTest.class.getResourceAsStream(file);
	    	ImageInputStream iis = ImageIO.createImageInputStream(stream);
	    	ImageReader reader = ImageIO.getImageReadersByFormatName(format).next();
	    	reader.setInput(iis, true);
	    	ImageReadParam params = reader.getDefaultReadParam();
	    	BufferedImage image = reader.read(0, params);
	    	reader.dispose();
	    	return image;
	    } catch (IOException e) {
	    	fail(e.getMessage());
	    	return null;
	    }
	}

	/**
	 * Measure the time it takes to apply a ParallelWatermarkFilter.
	 */
	private static double measureTime(IFilter pwf, BufferedImage inputImage) {
		Date startTime = new Date();
		pwf.apply(inputImage);
		Date endTime = new Date();
		return endTime.getTime() - startTime.getTime();
	}
	
}
