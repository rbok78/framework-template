package org.example.pages;

import io.qameta.allure.Step;
import org.example.utils.BasePage;
import org.example.utils.ImageUtils;
import org.opencv.core.Point;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.function.Function;

public class TheOnlyPanel extends BasePage {
    private final By moreInformationLinkBy = By.cssSelector("a[href]");
    private final By paragraphBy = By.cssSelector("p");

    /**
     * Constructor for page component.
     *
     * @param parentElement the parent element
     */
    public TheOnlyPanel(final WebElement parentElement) {
        super(parentElement);
    }

    /**
     * Returns paragraph that starts with a given text.
     */
    private final Function<String, WebElement> paragraphByStartWith = input -> {
        final String message = "No paragraph found. Start with: " + input;
        return $$(paragraphBy).stream().filter(p -> p.getText().startsWith(input))
                .findFirst().orElseThrow(() -> new NoSuchElementException(message));
    };

    @Step("Get more information link text")
    public String getMoreInformationLinkText() {
        waitForSelector(moreInformationLinkBy);
        final WebElement link = $(moreInformationLinkBy);
        return link.getText();
    }

    @Step("Get first paragraph text")
    public String getFirstParagraphText() {
        waitForFunction(paragraphByStartWith, "This");
        final WebElement paragraph = paragraphByStartWith.apply("This");
        return paragraph.getText();
    }

    @Step("Find more information link")
    public List<Point> findMoreInformationLink() {
        final WebElement panel = (WebElement) parentElement;
        return ImageUtils.matchTemplate(panel, "more-information-link", 0.95);
    }
}
