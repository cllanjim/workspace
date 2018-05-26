package org.iMage.shutterpile.cli;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.iMage.shutterpile.port.IWatermarker;

/**
 * * This class parses all command line parameters and creates an Image with watermark.
 *
 * @author Dominic Wolff
 *
 */
public final class App {

  /**
   * Private constructor: App is not to be instantiated from outside
   */
  private App() {
    throw new IllegalAccessError();
  }

  private static final String CMD_OPTION_ORIGINAL_IMAGE = "o";
  private static final String CMD_OPTION_WATERMARK_IMAGE = "w";
  private static final String CMD_OPTION_RETURN_IMAGE = "r";
  private static final String CMD_OPTION_WATERMARKS_PER_ROW = "n";


  /**
   * The main method.<br>
   * Possible arguments:<br>
   * <b>o</b> - the path to the original image to which the watermark is to be applied<br>
   * <b>w</b> - the path to the watermark image<br>
   * <b>r</b> - the path to returned image<br>
   * <b>n</b> - the number of watermarks in a line (default: 5) (see
   * {@link IWatermarker#generate(java.awt.image.BufferedImage, java.awt.image.BufferedImage, int)
   * IWatermarker#generate})<br>
   *
   * @param args
   *          the command line arguments
   */
  public static void main(String[] args) {
    CommandLine cmd = null;
    try {
      cmd = App.doCommandLineParsing(args);
    } catch (ParseException e) {
      System.err.println("Wrong command line arguments given: " + e.getMessage());
      System.exit(1);
    }
    //TODO: implement me!
  }

  /**
   * Parse and check command line arguments
   *
   * @param args
   *          command line arguments given by the user
   * @return CommandLine object encapsulating all options
   * @throws ParseException
   *           if wrong command line parameters or arguments are given
   */
  private static CommandLine doCommandLineParsing(String[] args) throws ParseException {
    Options options = new Options();
    Option opt;

    /*
     * Define command line options and arguments
     */
    opt = new Option(App.CMD_OPTION_ORIGINAL_IMAGE, "image-input", true, "path to input image");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_WATERMARK_IMAGE, "watermark-input", true,
        "path to watermark image");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_RETURN_IMAGE, "image-output", true, "path to output image");
    opt.setRequired(true);
    opt.setType(String.class);
    options.addOption(opt);

    opt = new Option(App.CMD_OPTION_WATERMARKS_PER_ROW, "count-per-row", true,
        "number of watermarks in a line");
    opt.setRequired(false);
    opt.setType(Integer.class);
    options.addOption(opt);

    CommandLineParser parser = new DefaultParser();
    return parser.parse(options, args);
  }

}
