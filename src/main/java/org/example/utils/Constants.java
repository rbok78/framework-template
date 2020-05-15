package org.example.utils;

import java.time.Duration;

public class Constants {

  public static final Duration GLOBAL_ASYNC_TIMEOUT = Duration.ofMillis(30_000L);
  public static final Duration GLOBAL_POLLING_INTERVAL = Duration.ofMillis(500L);
  public static final Duration GLOBAL_NETWORK_IDLE_TIMEOUT = Duration.ofMillis(1_000L);

  /**
   * Prevents this class to be instantiated.
   */
  private Constants() {
    throw new AssertionError();
  }
}
