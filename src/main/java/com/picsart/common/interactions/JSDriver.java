package com.picsart.common.interactions;

import com.picsart.common.DriverProvider;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;

public class JSDriver {

    public static void scrollToElement(WebElement element) {
        ((JavascriptExecutor) DriverProvider.getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public static void scrollToDown() {
        ((JavascriptExecutor) DriverProvider.getDriver()).executeScript("window.scrollTo(0, document.body.scrollHeight)");
    }

    public static String getCanvasData(WebElement element) {
        return (String) ((JavascriptExecutor) DriverProvider.getDriver()).executeScript("return arguments[0].toDataURL('image/png').substring(22);", element);
    }
}
