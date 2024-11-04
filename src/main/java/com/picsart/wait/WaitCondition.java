package com.picsart.wait;

import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;

public abstract class WaitCondition {

    public static final WaitCondition visible = new Visible();
    public static final WaitCondition exist = new Exist();
    public static final WaitCondition enabled = new Enabled();

    public static WaitCondition not(WaitCondition condition) {
        return new Not(condition);
    }

    public abstract Function<By, Boolean> condition(SearchContext searchContext);
    public abstract Function<WebElement, Boolean> conditionToElement(SearchContext searchContext);
}
