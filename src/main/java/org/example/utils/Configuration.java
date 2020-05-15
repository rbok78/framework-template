package org.example.utils;

import java.util.function.BinaryOperator;

public class Configuration {

  private static final BinaryOperator<String> p = System::getProperty;
  public static final String BROWSER = p.apply("browser", "chrome");
  public static final String GRID_URL = p.apply("gridUrl", "");
  public static final String FRONTEND_BASE_URL = p.apply("frontUrl", "http://example.org/");
  public static final String CONCURRENCY = p.apply("concurrency", availableProcessors());

  /**
   * Prevents this class to be instantiated.
   */
  private Configuration() {
    throw new AssertionError();
  }

  /**
   * Gets the number of available processors.
   *
   * @return the count
   */
  private static String availableProcessors() {
    return String.valueOf(Runtime.getRuntime().availableProcessors());
  }
}
