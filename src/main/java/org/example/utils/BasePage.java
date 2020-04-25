package org.example.utils;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.FluentWait;

import java.util.List;
import java.util.function.Function;

import static org.example.utils.Constants.GLOBAL_ASYNC_TIMEOUT;
import static org.example.utils.Constants.GLOBAL_POLLING_INTERVAL;

public abstract class BasePage {
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
