package org.example.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Function;

import static org.example.utils.Constants.*;

public abstract class BasePage {
    private static final Logger logger = LoggerFactory.getLogger(BasePage.class);
    protected SearchContext parentElement;

    /**
     * Constructor for page.
     */
    public BasePage() {
        this.parentElement = BaseTest.getDriver();
    }

    /**
     * Constructor for page component.
     *
     * @param parentElement the parent element
     */
    public BasePage(final WebElement parentElement) {
        this.parentElement = parentElement;
    }

    /**
     * Waits for minimum one element to be present.
     *
     * @param selector the selector
     */
    public void waitForSelector(final By selector) {
        new FluentWait<>(parentElement)
                .withMessage("Timed out waiting for selector " + selector)
                .withTimeout(GLOBAL_ASYNC_TIMEOUT)
                .pollingEvery(GLOBAL_POLLING_INTERVAL)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .until(p -> p.findElements(selector).size() > 0);
    }

    /**
     * Waits until the function returns neither null nor false.
     *
     * @param function the function
     * @param input    the input to the function
     * @param <T>      the type of the input to the function
     * @param <R>      the type of the result of the function
     */
    public <T, R> void waitForFunction(final Function<T, R> function, final T input) {
        new FluentWait<>(input)
                .withMessage("Timed out waiting for function")
                .withTimeout(GLOBAL_ASYNC_TIMEOUT)
                .pollingEvery(GLOBAL_POLLING_INTERVAL)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NoSuchElementException.class)
                .until(function);
    }

    /**
     * Gets the start time of the last performance entry.
     *
     * @return the start time
     */
    private double getLastPerformanceEntryStartTime() {
        final String script = "return window.performance.getEntries().pop().startTime;";
        final JavascriptExecutor executor = (JavascriptExecutor) BaseTest.getDriver();
        final String result = executor.executeScript(script).toString();
        final double startTime = Double.parseDouble(result);
        logger.debug("Last performance entry started at " + startTime);
        return startTime;
    }

    /**
     * Waits for all requests to finish.
     */
    public void waitForNetworkIdle() {
        final long timeout = GLOBAL_NETWORK_IDLE_TIMEOUT.toMillis();
        final long startTime = System.currentTimeMillis();
        double entryStartTime = getLastPerformanceEntryStartTime();
        long endTime = startTime + timeout;

        while (System.currentTimeMillis() < endTime) {
            final double nextEntryStartTime = getLastPerformanceEntryStartTime();
            if (nextEntryStartTime > entryStartTime) {
                logger.debug("Reset timer after new performance entries found");
                endTime = System.currentTimeMillis() + timeout;
                entryStartTime = nextEntryStartTime;
            }
        }

        final String duration = Long.toString(System.currentTimeMillis() - startTime);
        logger.debug("Waited " + duration + " milliseconds for network idle");
    }

    /**
     * Gets the DOM high resolution timestamp to filter requests
     * in {@link #waitForRequest(String, double, int)}.
     *
     * @return the timestamp
     */
    public double getDomHighResTimeStamp() {
        final String script = "return window.performance.now();";
        final JavascriptExecutor executor = (JavascriptExecutor) BaseTest.getDriver();
        final double timestamp = Double.parseDouble(executor.executeScript(script).toString());
        logger.debug("Timestamp is " + timestamp);
        return timestamp;
    }

    /**
     * Waits for specific requests to finish.
     *
     * @param url       the fragment of the request URL
     * @param startTime the minimum start time of the request
     * @param count     the minimum number of requests to find
     */
    public void waitForRequest(final String url, final double startTime, final int count) {
        final String script = "return window.performance.getEntries()" +
                ".filter(function(entry){return entry.startTime >= " + startTime + ";})" +
                ".filter(function(entry){return entry.name.includes(\"" + url + "\");})" +
                ".length >= " + count + ";";

        new FluentWait<>((JavascriptExecutor) BaseTest.getDriver())
                .withMessage("Timed out waiting for request to finish")
                .withTimeout(GLOBAL_ASYNC_TIMEOUT)
                .pollingEvery(GLOBAL_POLLING_INTERVAL)
                .until(e -> e.executeScript(script));
    }

    /**
     * Waits for specific requests to finish.
     *
     * @param action the front-end action to run
     * @param url    the fragment of the request URL
     * @param count  the minimum number of requests to find
     */
    public void waitForRequest(final Action action, final String url, final int count) {
        final double timestamp = getDomHighResTimeStamp();
        action.run();
        waitForRequest(url, timestamp, count);
    }

    /**
     * Returns one element for a given selector.
     *
     * @param selector the selector
     * @return the element
     */
    public WebElement $(final By selector) {
        return parentElement.findElement(selector);
    }

    /**
     * Returns a list of elements for a given selector.
     *
     * @param selector the selector
     * @return the list of elements
     */
    public List<WebElement> $$(final By selector) {
        return parentElement.findElements(selector);
    }
}
