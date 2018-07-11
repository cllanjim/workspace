package org.iMage.shutterpile.parallel.acceptance;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.time.StopWatch;
import org.iMage.shutterpile.impl.filters.WatermarkFilter;
import org.iMage.shutterpile.port.IFilter;
import org.iMage.shutterpile_parallel.impl.filters.BetterParallelWatermarkFilter;
import org.iMage.shutterpile_parallel.impl.filters.ParallelWatermarkFilter;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Performanzermittlung
 *
 * @author tk
 * @author Mathias
 * @author weigelt
 */
public class IPDPerformanceTest {

  /** Anzahl der Messläufe. */
  private static final int NUMLOOPS = 5;

  /** Alle zu testenden Fadenanzahlen. */
  private Integer[] threads = { 1, 2, 4, 8, 16, 32, 64, 128 };

  private static BufferedImage testImage;
  private static BufferedImage watermarkImage;

  @BeforeClass
  public static void setUp() throws IOException {
    testImage = ImageIO.read(new File("src/test/resources/NF10_Bild.PNG"));
    watermarkImage = ImageIO
        .read(new File("src/test/resources/Parallel_Wettbewerb_Wasserzeichen.PNG"));
  }

  @Test
  //@Ignore
  public void testPerformance30Parallel() {
    testPerformanceParallel(30);
  }

  @Test
  //@Ignore
  public void testPerformance30Sequential() {
    testPerformanceSequential(30);
  }

  protected void testPerformanceParallel(int numOfWatermarks) {
    long[] measurements = new long[threads.length];

    System.out.println("Performanzmessung");
    for (int t = 0; t < threads.length; t++) {
      System.out.println("Starte Messung für " + threads[t] + " Fäden");
      int curThreadCount = threads[t];
      // Für mehr Messgenauigkeit :)
      StopWatch sw = new StopWatch();
      for (int loop = 0; loop < NUMLOOPS; loop++) {
        IFilter filter = new BetterParallelWatermarkFilter(watermarkImage, numOfWatermarks, curThreadCount);
        // Start der Messung
        sw.reset();
        sw.start();
        BufferedImage destImage = filter.apply(testImage);

        // Ende der Messung
        sw.stop();
        measurements[t] += sw.getTime();
      }
      measurements[t] /= NUMLOOPS;
    }
    System.out.println("Messergebnisse:");
    System.out.println(Arrays.toString(measurements));
  }

  protected void testPerformanceSequential(int numOfWatermarks) {
    long measurements = 0;

    System.out.println("Performanzmessung Sequential");
    IFilter filter = new WatermarkFilter(watermarkImage, numOfWatermarks);

    // Für mehr Messgenauigkeit :)
    StopWatch sw = new StopWatch();
    for (int loop = 0; loop < NUMLOOPS; loop++) {
      // Start der Messung
      sw.reset();
      sw.start();
      BufferedImage destImage = filter.apply(testImage);

      // Ende der Messung
      sw.stop();
      measurements += sw.getTime();
    }
    measurements /= NUMLOOPS;
    System.out.println("Messergebnisse:");
    System.out.println(measurements);
  }
}
