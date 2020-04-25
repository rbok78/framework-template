package org.example.utils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.function.UnaryOperator;

@ExtendWith(ScreenshotExtension.class)
public abstract class BaseTest {
    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);
    private static final ThreadLocal<WebDriver> storedDriver = new ThreadLocal<>();

    @BeforeEach
    public void setUp(final TestInfo testInfo) throws MalformedURLException {
        final UnaryOperator<String> message = browser ->
                "Browser is not implemented: " + browser;

        if (Configuration.BROWSER.equals("chrome")) {
            if (Configuration.GRID_URL.isEmpty()) {
                final ChromeOptions chromeOptions = getChromeOptions();
                final ChromeDriver driver = new ChromeDriver(chromeOptions);
                driver.manage().window().maximize();
                driver.get(Configuration.FRONTEND_BASE_URL);
                storedDriver.set(driver);
            } else {
                final URL url = new URL(Configuration.GRID_URL);
                final ChromeOptions chromeOptions = getChromeOptions();
                final RemoteWebDriver driver = new RemoteWebDriver(url, chromeOptions);
                driver.setFileDetector(new LocalFileDetector());
                driver.manage().window().maximize();
                driver.get(Configuration.FRONTEND_BASE_URL);
                storedDriver.set(driver);
            }
        } else {
            throw new IllegalStateException(message.apply(Configuration.BROWSER));
        }

        logger.info("Set up completed for {}", getMethodName(testInfo));
    }

    @AfterEach
    public void tearDown(final TestInfo testInfo) {
        storedDriver.get().quit();
        storedDriver.remove();
        logger.info("Tear down completed for {}", getMethodName(testInfo));
    }

    /**
     * Returns the thread safe driver.
     *
     * @return the driver
     */
    public static WebDriver getDriver() {
        return storedDriver.get();
    }

    /**
     * Returns default ChromeDriver capabilities.
     *
     * @return the capabilities
     */
    private ChromeOptions getChromeOptions() {
        final ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        return chromeOptions;
    }

    /**
     * Returns the name of test method.
     *
     * @param testInfo the information about the currently running test
     * @return the name
     */
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    private String getMethodName(final TestInfo testInfo) {
        return testInfo.getTestMethod().get().getName();
    }
}
