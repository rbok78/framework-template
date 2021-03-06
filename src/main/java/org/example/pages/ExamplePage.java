package org.example.pages;

import io.qameta.allure.Step;
import org.example.utils.BasePage;
import org.example.utils.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class ExamplePage extends BasePage {

  private final By theOnlyPanelBy = By.cssSelector("div");

  /**
   * Gets the example page title.
   *
   * @return the title
   */
  @Step("Get example page title")
  public String getExamplePageTitle() {
    final WebDriver driver = BaseTest.getDriver();
    return driver.getTitle();
  }

  /**
   * Gets the only panel.
   *
   * @return the panel
   */
  @Step("Get the only panel")
  public TheOnlyPanel getTheOnlyPanel() {
    waitForSelector(theOnlyPanelBy);
    final WebElement panel = $(theOnlyPanelBy);
    return new TheOnlyPanel(panel);
  }
}
