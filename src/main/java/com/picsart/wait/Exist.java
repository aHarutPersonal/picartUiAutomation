package com.picsart.wait;

import java.util.NoSuchElementException;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class Exist extends WaitCondition {
    @Override
    public Function<By, Boolean> condition(SearchContext searchContext) {
        return (by -> !searchContext.findElements(by).isEmpty());
    }

    @Override
    public Function<WebElement, Boolean> conditionToElement(SearchContext searchContext) {
        return webElement -> {
            try {
                webElement.isDisplayed();
            } catch (NoSuchElementException e) {
                return false;
            }
            return true;
        };
    }
}
