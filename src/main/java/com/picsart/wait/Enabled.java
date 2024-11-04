package com.picsart.wait;

import java.util.function.Function;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
public class Enabled extends WaitCondition {

    @Override
    public Function<By, Boolean> condition(SearchContext searchContext) {
        return by -> searchContext.findElement(by).isEnabled();
    }

    @Override
    public Function<WebElement, Boolean> conditionToElement(SearchContext searchContext) {
        return WebElement::isEnabled;
    }
}
