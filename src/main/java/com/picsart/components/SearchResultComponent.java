package com.picsart.components;

import static com.picsart.wait.WaitCondition.enabled;
import static com.picsart.wait.WaitCondition.visible;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.Objects;
import javax.imageio.ImageIO;

import com.picsart.base.BaseComponent;
import com.picsart.pages.EditorPage;
import com.picsart.pages.SearchPage;
import lombok.SneakyThrows;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchResultComponent extends BaseComponent<SearchResultComponent, SearchPage> {

    @FindBy(xpath = ".//button[contains(@id, 'like_button_item')]")
    private WebElement likeButton;

    @FindBy(xpath = ".//button[contains(@id, 'save_button_item')]")
    private WebElement saveButton;

    @FindBy(xpath = ".//button[contains(@id, 'try_now_button_item')]")
    private WebElement tryNowButton;

    @FindBy(xpath = ".//div[@data-testid='premium-icon-root']")
    private WebElement premiumIcon;

    @FindBy(xpath = ".//img[@data-testid='image']")
    private WebElement image;

    public SearchResultComponent(WebElement searchContext, SearchPage page) {
        super(searchContext, page);
    }

    public boolean isLikeButtonDisplayed() {
        return is(likeButton, visible);
    }

    public boolean isSaveButtonDisplayed() {
        return is(saveButton, visible);
    }

    public boolean isTryNowButtonDisplayed() {
        return is(tryNowButton, visible);
    }

    public boolean isPremiumIconDisplayed() {
        return is(premiumIcon, visible);
    }

    public EditorPage clickOnTryNowButton() {
        tryNowButton.click();
        return new EditorPage().get();
    }

    public void clickOnLikeButton() {
        waitFor(likeButton, enabled).click();
    }

    @SneakyThrows
    public BufferedImage getImage() {
        return ImageIO.read(new URI(Objects.requireNonNull(image.getAttribute("src"))).toURL());
    }
}
