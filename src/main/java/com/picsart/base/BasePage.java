package com.picsart.base;

import static com.picsart.common.PropertiesManager.getProperty;
import static com.picsart.common.enums.PropertyNames.URL;
import static com.picsart.wait.WaitCondition.visible;

import com.picsart.common.CustomLoadableComponent;
import com.picsart.common.DriverProvider;
import com.picsart.components.RegistrationOverlayComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract non-sealed class BasePage<T extends BasePage<T>> extends BaseUiAbstraction<WebDriver> implements CustomLoadableComponent<T> {
    private final String endpoint;
    protected By frame;

    @FindBy(xpath = "//div[@data-testid='registration-modal-container']")
    private WebElement registrationOverlay;

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    public BasePage(String endpoint) {
        super(DriverProvider.getDriver());
        this.endpoint = endpoint;
    }

    public String getCurrentUrl() {
        return base.getCurrentUrl();
    }

    protected String getExpectedUrl() {
        return String.join("/", getProperty(URL.name()), endpoint);
    }

    protected abstract void waitForPageToLoad();

    public RegistrationOverlayComponent<T> getRegistrationOverlay() {
        switchToDefaultContent();
        return new RegistrationOverlayComponent<T>(registrationOverlay, (T) this);
    }

    public T acceptCookies() {
        switchToDefaultContent();
        waitFor(acceptCookiesButton, visible).click();
        switchToFrame();
        return (T) this;
    }

    public T refresh() {
        base.navigate().refresh();
        initElements();
        switchToFrame();
        waitForPageToLoad();
        return (T) this;
    }

    @Override
    public void load() {
        base.get(getExpectedUrl());
        initElements();
    }

    @Override
    public void isLoaded() throws Error {
        if (!getCurrentUrl().startsWith(getExpectedUrl())) {
            throw new Error("The page is not loaded");
        }
        switchToFrame();
        waitForPageToLoad();
    }

    public T switchToFrame() {
        if (frame == null) {
            switchToDefaultContent();
        } else {
            wait
                .withTimeout(DEFAULT_TIMEOUT)
                .until(driver -> ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame).apply(driver));
        }
        return (T) this;
    }

    protected void switchToDefaultContent() {
        base.switchTo().defaultContent();
    }
}
