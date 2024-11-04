package com.picsart.components;

import com.picsart.base.BaseComponent;
import com.picsart.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class RegistrationOverlayComponent<T extends BasePage<T>> extends BaseComponent<RegistrationOverlayComponent<T>, T> {
    @FindBy(xpath = ".//*[@data-testid='modal-close-icon']")
    private WebElement closeButton;

    public RegistrationOverlayComponent(WebElement searchContext, T page) {
        super(searchContext, page);
    }

    public T close() {
        closeButton.click();
        return page.get();
    }
}
