package org.example.utils;

import static org.opencv.core.CvType.CV_32FC1;

import java.io.File;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import nu.pattern.OpenCV;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebElement;

public class ImageUtils {

  private static final Logger logger = LogManager.getLogger(ImageUtils.class);

  // Load the native opencv library
  static {
    OpenCV.loadShared();
  }

  /**
   * Finds all matches in the image.
   *
   * @param img       the image to be searched
   * @param tmp       the template to be found
   * @param threshold the minimal threshold to match the template
   * @return the list of central points of all matches. Empty list is returned if there is no match.
   */
  public static List<Point> matchTemplate(final Mat img, final Mat tmp, final double threshold) {
    final List<Point> centralPoints = new ArrayList<>();

    // Create the result matrix
    final int result_cols = img.cols() - tmp.cols() + 1;
    final int result_rows = img.rows() - tmp.rows() + 1;
    final Mat result = new Mat(result_rows, result_cols, CV_32FC1);

    // Do the matching and normalize
    final int matchMethod = Imgproc.TM_CCOEFF_NORMED;
    Imgproc.matchTemplate(img, tmp, result, matchMethod);

    while (true) {

      // Localize the best match with minMaxLoc
      final Core.MinMaxLocResult mmr = Core.minMaxLoc(result);
      final String bestMatchMessage = "Found best match. maxVal: {}, threshold: {}";
      logger.debug(bestMatchMessage, mmr.maxVal, threshold);
      final Point topLeftLoc = mmr.maxLoc;
      if (mmr.maxVal >= threshold) {

        // Save match coordinates
        final double xCentre = topLeftLoc.x + Math.round(tmp.cols() / 2.0);
        final double yCentre = topLeftLoc.y + Math.round(tmp.rows() / 2.0);
        final String matchMessage = "Found match over threshold. xCentre: {}, yCentre: {}";
        logger.debug(matchMessage, xCentre, yCentre);
        centralPoints.add(new Point(xCentre, yCentre));

        // Hide match and continue
        final double xBottomRight = topLeftLoc.x + tmp.cols();
        final double yBottomRight = topLeftLoc.y + tmp.rows();
        final Point bottomRightLoc = new Point(xBottomRight, yBottomRight);
        Imgproc.rectangle(result, topLeftLoc, bottomRightLoc, new Scalar(0, 255, 0), -1);

      } else {
        break;
      }
    }

    return centralPoints;
  }

  /**
   * Finds all matches in the element.
   *
   * @param element      the element to be searched
   * @param templateName the name of the resource without extension
   * @param threshold    the minimal threshold to match the template
   * @return the list of central points of all matches. Empty list is returned if there is no match.
   */
  public static List<Point> matchTemplate(final WebElement element,
      final String templateName, final double threshold) {
    final Mat tmp;

    // Load template
    try {
      final String resourcePath = "templates/" + templateName + ".png";
      final URL resource = ClassLoader.getSystemClassLoader().getResource(resourcePath);
      Objects.requireNonNull(resource, "Failed to load " + resourcePath);
      final String templatePath = Paths.get(resource.toURI()).toFile().getAbsolutePath();
      tmp = Imgcodecs.imread(templatePath, Imgcodecs.IMREAD_COLOR);
    } catch (final java.net.URISyntaxException e) {
      throw new RuntimeException(e);
    }

    // Screenshot element
    final File screenshot = element.getScreenshotAs(OutputType.FILE);
    final String screenshotPath = screenshot.getAbsolutePath();
    final Mat img = Imgcodecs.imread(screenshotPath, Imgcodecs.IMREAD_COLOR);

    // Match template
    return matchTemplate(img, tmp, threshold);
  }
}
