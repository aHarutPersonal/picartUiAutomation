package com.picsart.base;

import static com.picsart.common.interactions.Actions.moveToElement;
import static com.picsart.wait.WaitCondition.visible;

import org.openqa.selenium.WebElement;

public non-sealed class BaseComponent<T extends BaseComponent<T, K>, K extends BasePage<K>> extends BaseUiAbstraction<WebElement> {
    protected final K page;
    public BaseComponent(WebElement searchContext, K page) {
        super(searchContext);
        this.page = page;
        initElements();
    }

    public T hover() {
        moveToElement(scrollToElement(base));
        return (T) this;
    }

    public T verifyIsDisplayed() {
        waitFor(base, visible);
        return (T) this;
    }
}
