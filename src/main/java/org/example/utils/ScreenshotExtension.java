package org.example.utils;

import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.UUID;

public class ScreenshotExtension implements AfterTestExecutionCallback {
    private static final Logger logger = LoggerFactory.getLogger(ScreenshotExtension.class);

    @Override
    public void afterTestExecution(final ExtensionContext extensionContext) {
        if (extensionContext.getExecutionException().isPresent()) {
            final TakesScreenshot driver = (TakesScreenshot) BaseTest.getDriver();
            final byte[] screenshot = driver.getScreenshotAs(OutputType.BYTES);
            final ByteArrayInputStream stream = new ByteArrayInputStream(screenshot);
            Allure.addAttachment(UUID.randomUUID().toString(), stream);
            final String methodName = extensionContext.getRequiredTestMethod().getName();
            logger.info("Logged screenshot for {}", methodName);
        }
    }
}
