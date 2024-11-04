package com.picsart.base;

import static com.picsart.wait.WaitCondition.enabled;
import static com.picsart.wait.WaitCondition.not;
import static com.picsart.wait.WaitCondition.visible;

import java.time.Duration;
import java.util.function.Function;

import com.picsart.common.interactions.JSDriver;
import com.picsart.wait.WaitCondition;
import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.FluentWait;

public abstract sealed class BaseUiAbstraction<T extends SearchContext> permits BaseComponent, BasePage {
    private static final String BUTTON_BY_NAME_XPATH = ".//button[text()='%s']";
    private static final By LOADER = By.xpath("//div[@data-testid='loader']");
    public static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(10);
    protected final FluentWait<T> wait;
    protected final T base;

    public BaseUiAbstraction(T searchContext) {
        this.base = searchContext;
        // Since the ExplicitWait supports only waits for webDriver, we need to use FluentWait as it is more generic
        this.wait = new FluentWait<>(searchContext).pollingEvery(Duration.ofMillis(500)).ignoring(Throwable.class);
    }

    protected void clickOnButtonByName(String name) {
        var buttonXpath = By.xpath(BUTTON_BY_NAME_XPATH.formatted(name));
        waitFor(buttonXpath, enabled);
        scrollToElement(base.findElement(buttonXpath)).click();
    }

    protected WebElement scrollToElement(WebElement element) {
        waitFor(element, visible);
        JSDriver.scrollToElement(element);
        return element;
    }

    protected void waitForLoaders() {
        try {
            waitFor(LOADER, visible, Duration.ofSeconds(1));
            waitFor(LOADER, not(visible));
        } catch (Exception ignored) {
            // ignore
        }
    }

    protected boolean is(WebElement element, WaitCondition condition) {
        return is(element, condition, Duration.ofMillis(500));
    }

    public boolean is(WebElement element, WaitCondition condition, Duration timeout) {
        try {
            waitFor(element, condition, timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean is(Function<T, Boolean> condition, Duration timeout) {
        try {
            waitFor(condition, timeout);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected boolean is(Function<T, Boolean> condition) {
        return is(condition, DEFAULT_TIMEOUT);
    }

    protected void waitFor(By by, WaitCondition condition, Duration timeout) {
        wait.withTimeout(timeout).until(base -> condition.condition(base).apply(by));
    }

    protected WebElement waitFor(WebElement element, WaitCondition condition, Duration timeout) {
        wait.withTimeout(timeout).until(base -> condition.conditionToElement(base).apply(element));
        return element;
    }

    protected void waitFor(Function<T, Boolean> condition, Duration timeout) {
        wait.withTimeout(timeout).until(condition);
    }

    protected void waitFor(By by, WaitCondition condition) {
        waitFor(by, condition, DEFAULT_TIMEOUT);
    }

    protected WebElement waitFor(WebElement element, WaitCondition condition) {
        waitFor(element, condition, DEFAULT_TIMEOUT);
        return element;
    }

    protected void waitFor(Function<T, Boolean> condition) {
        waitFor(condition, DEFAULT_TIMEOUT);
    }

    protected void initElements() {
        PageFactory.initElements(base, this);
    }
}
