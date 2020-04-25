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

import static org.junit.jupiter.api.Assertions.assertEquals;

@Epic("As a tester, I want to have an example page so that I can test it")
@DisplayName("Example domain test suite")
public class ExampleDomainTests extends BaseTest {

    @Test
    @Story("As a tester, I want to see a meaningful page title so that I can test it")
    @DisplayName("Meaningful page title test")
    @Tag("smoke-test")
    public void pageTitleTest() {
        final ExamplePage examplePage = new ExamplePage();
        final String pageTitle = examplePage.getExamplePageTitle();
        assertEquals("Example Domain", pageTitle);
    }

    @Test
    @Story("As a tester, I want to see a meaningful link text so that I can test it")
    @DisplayName("Meaningful link text test")
    public void linkTextTest() {
        final ExamplePage examplePage = new ExamplePage();
        final TheOnlyPanel theOnlyPanel = examplePage.getTheOnlyPanel();
        final String text = theOnlyPanel.getMoreInformationLinkText();
        assertEquals("More information...", text);
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
    @Story("As a tester, I want to log a screenshot for failed tests so that I can view it")
    @DisplayName("Fail test with screenshot")
    public void failTestWithScreenshot() {
        assertEquals("foo", "boo");
    }
}
