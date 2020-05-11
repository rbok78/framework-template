package org.example;

import io.qameta.allure.Epic;
import io.qameta.allure.Story;
import org.example.pages.ExamplePage;
import org.example.pages.TheOnlyPanel;
import org.example.utils.BaseTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.opencv.core.Point;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.example.utils.Constants.GLOBAL_NETWORK_IDLE_TIMEOUT;

@Epic("As a tester, I want to have an example app so that I can test it")
@DisplayName("Example gui test suite")
public class ExampleGuiTests extends BaseTest {

    @Test
    @Story("As a tester, I want to see a meaningful page title so that I can test it")
    @DisplayName("Meaningful page title test")
    @Tag("smoke-test")
    public void pageTitleTest() {
        final ExamplePage examplePage = new ExamplePage();
        final String pageTitle = examplePage.getExamplePageTitle();
        Assertions.assertEquals("Example Domain", pageTitle);
    }

    @Test
    @Story("As a tester, I want to see a meaningful link text so that I can test it")
    @DisplayName("Meaningful link text test")
    public void linkTextTest() {
        final ExamplePage examplePage = new ExamplePage();
        final TheOnlyPanel theOnlyPanel = examplePage.getTheOnlyPanel();
        final String text = theOnlyPanel.getMoreInformationLinkText();
        Assertions.assertEquals("More information...", text);
    }

    @Test
    @Story("As a tester, I want to see a meaningful paragraph text so that I can test it")
    @DisplayName("Meaningful paragraph text test")
    public void paragraphTextTest() {
        final ExamplePage examplePage = new ExamplePage();
        final TheOnlyPanel theOnlyPanel = examplePage.getTheOnlyPanel();
        final String text = theOnlyPanel.getFirstParagraphText();
        Assertions.assertTrue(text.startsWith("This domain is for use in illustrative examples"));
    }

    @Test
    @Story("As a tester, I want to know when all requests are finished so that I can wait for it")
    @DisplayName("Wait for network idle test")
    public void waitForNetworkIdleTest() {
        final ExamplePage examplePage = new ExamplePage();
        final Instant start = Instant.now();
        examplePage.waitForNetworkIdle();
        final Duration duration = Duration.between(start, Instant.now());
        Assertions.assertTrue(duration.compareTo(GLOBAL_NETWORK_IDLE_TIMEOUT) >= 0);
    }

    @Test
    @Story("As a tester, I want to get a timestamp so that I can filter requests")
    @DisplayName("Get timestamp test")
    public void getDomHighResTimeStampTest() {
        final ExamplePage examplePage = new ExamplePage();
        final double timestamp = examplePage.getDomHighResTimeStamp();
        Assertions.assertTrue(timestamp > 0);
    }

    @Test
    @Story("As a tester, I want to know when a request is finished so that I can wait for it")
    @DisplayName("Wait for request test")
    public void waitForRequestTest() {
        final ExamplePage examplePage = new ExamplePage();
        Assertions.assertDoesNotThrow(() -> examplePage
                .waitForRequest("favicon", 0.0, 1));
    }

    @Test
    @Story("As a tester, I want to find a template in the canvas so that I click on it")
    @DisplayName("Match template test")
    public void matchTemplateTest() {
        final ExamplePage examplePage = new ExamplePage();
        final TheOnlyPanel theOnlyPanel = examplePage.getTheOnlyPanel();
        final List<Point> centralPoints = theOnlyPanel.findMoreInformationLink();
        Assertions.assertEquals(1, centralPoints.size());
    }

    @Test
    @Story("As a tester, I want to log a screenshot for failed tests so that I can view it")
    @DisplayName("Fail test with screenshot")
    public void failTestWithScreenshot() {
        Assertions.assertEquals("foo", "boo");
    }
}
