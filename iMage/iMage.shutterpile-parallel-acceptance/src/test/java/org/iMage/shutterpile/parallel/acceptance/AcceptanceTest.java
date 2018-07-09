package org.iMage.shutterpile.parallel.acceptance;

import java.awt.image.BufferedImage;

import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile_parallel.impl.ParallelWatermarkFilterTest;
import org.iMage.shutterpile_parallel.impl.filters.ParallelWatermarkFilter;
import org.junit.BeforeClass;
import org.junit.Test;

public class AcceptanceTest extends TestBase{

	private static String[] inputImagePaths = {"/F10_Eingabe.png", "/F10_WZ.png", "/F10_Aufgabe.png", "NF10_WZ.png", "NF10_Bild.png"};
	private static BufferedImage[] testImages;

	/**
	 * Prepare the tests: Load the test-images.
	 */
	@BeforeClass
	public static void loadImages() {
		testImages = new BufferedImage[inputImagePaths.length];
		for(int i = 0; i < inputImagePaths.length; i++) {
			testImages[i] = ParallelWatermarkFilterTest.loadImage(inputImagePaths[i], "png");
		}
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
		IFilter seqWMF = new ParallelWatermarkFilter(testImages[1],2,1);
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
		
	}

	/**
	 * NF10
	 * 
	 * Die Ausführzeit des sequentiellen Wasserzeichenfilters für das Bild NF10_Bild.PNG, 
	 * das Wasserzeichen-Bild NF10_WZ.PNG und 30 Wasserzeichen pro Reihe beträgt höchstens 1,5 Sekunden.
	 */
	@Test
	public void durationTimeLimit() {
		
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
		
	}

	/**
	 * NF40
	 * 
	 * Die maximale Abweichung bei 10 Durchläufen des parallelen Wasserzeichenfilters mit 
	 * zwei Ausführungsfäden für das Bild NF10_Bild.PNG, das Wasserzeichen-Bild 
	 * NF10_WZ.PNG und 30 Wasserzeichen pro Reihe beträgt pro Lauf höchstens 100 Millisekunden.
	 */
	@Test
	public void maximalVariationLimit() {
		
	}


}
