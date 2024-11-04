package com.picsart.wait;

import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public class Not extends WaitCondition {
    private final WaitCondition condition;

    Not(WaitCondition condition) {
        this.condition = condition;
    }

    @Override
    public Function<By, Boolean> condition(SearchContext searchContext) {
        return by -> !condition.condition(searchContext).apply(by);
    }

    @Override
    public Function<WebElement, Boolean> conditionToElement(SearchContext searchContext) {
        return webElement -> !condition.conditionToElement(searchContext).apply(webElement);
    }
}
