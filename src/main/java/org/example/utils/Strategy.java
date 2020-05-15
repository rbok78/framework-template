package org.example.utils;

import org.junit.platform.engine.ConfigurationParameters;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfiguration;
import org.junit.platform.engine.support.hierarchical.ParallelExecutionConfigurationStrategy;

public class Strategy implements ParallelExecutionConfiguration,
    ParallelExecutionConfigurationStrategy {

  @Override
  public int getParallelism() {
    return Integer.parseInt(Configuration.CONCURRENCY);
  }

  @Override
  public int getMinimumRunnable() {
    return Integer.parseInt(Configuration.CONCURRENCY);
  }

  @Override
  public int getMaxPoolSize() {
    return Integer.parseInt(Configuration.CONCURRENCY);
  }

  @Override
  public int getCorePoolSize() {
    return Integer.parseInt(Configuration.CONCURRENCY);
  }

  @Override
  public int getKeepAliveSeconds() {
    return 30;
  }

  @Override
  public ParallelExecutionConfiguration createConfiguration(
      final ConfigurationParameters configurationParameters) {
    return this;
  }
}
