package com.picsart.common.interactions;

import com.picsart.common.DriverProvider;
import org.openqa.selenium.WebElement;

public class Actions {
    private static final ThreadLocal<org.openqa.selenium.interactions.Actions> ACTIONS_THREAD_LOCAL = new ThreadLocal<>();

    public static org.openqa.selenium.interactions.Actions getActions() {
        if (ACTIONS_THREAD_LOCAL.get() == null) {
            ACTIONS_THREAD_LOCAL.set(new org.openqa.selenium.interactions.Actions(DriverProvider.getDriver()));
        }
        return ACTIONS_THREAD_LOCAL.get();
    }

    public static void moveToElement(WebElement element) {
        getActions().moveToElement(element).perform();
    }

    public static void click(WebElement element) {
        getActions().click(element).perform();
    }
}
