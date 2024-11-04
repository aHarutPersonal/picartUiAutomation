package com.picsart.pages;

import static com.picsart.wait.WaitCondition.not;
import static com.picsart.wait.WaitCondition.visible;

import java.time.Duration;
import java.util.List;

import com.picsart.base.BasePage;
import com.picsart.common.interactions.JSDriver;
import com.picsart.components.SearchFiltersComponent;
import com.picsart.components.SearchResultComponent;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchPage extends BasePage<SearchPage> {
    private static final By SEARCH_RESULTS = By.xpath("//h1[text()='Search results']");

    @FindBy(xpath = "//div[@data-testid='search-card-root']")
    private List<WebElement> searchResults;

    @FindBy(xpath = "//div[@data-automation='search-item-premium']")
    private List<WebElement> premiumSearchResults;

    @FindBy(xpath = "//div[@data-testid='skeleton']")
    private List<WebElement> skeletons;

    @FindBy(xpath = "//div[@data-testid='search-filter-root']")
    private WebElement searchFilters;

    @FindBy(id = "filter_icon")
    private WebElement filterIcon;

    public SearchPage() {
        super("search");
        frame = By.xpath("//iframe[contains(@id, 'miniapp-frame')]");
    }

    @Override
    public void waitForPageToLoad() {
        waitFor(SEARCH_RESULTS, visible);
        waitFor(base -> skeletons.isEmpty());
        waitFor(base -> searchResults.stream().allMatch(WebElement::isDisplayed));
    }

    public SearchPage waitForSearchResultsUpdated() {
        if(is(base -> !skeletons.isEmpty(), Duration.ofSeconds(2))) {
            waitFor(base -> skeletons.isEmpty());
        }
        return this;
    }

    public SearchPage waitForPremiumSearchResults() {
        while (is(base -> premiumSearchResults.isEmpty())) {
            JSDriver.scrollToDown();
            waitForLoaders();
        }
        return this;
    }

    public List<SearchResultComponent> getSearchResults() {
        return searchResults.stream()
                .filter(WebElement::isDisplayed)
                .map(element -> new SearchResultComponent(element, this))
                .toList();
    }

    public List<SearchResultComponent> getPremiumSearchResults() {
        return premiumSearchResults.stream()
                .map(element -> new SearchResultComponent(element, this))
                .toList();
    }

    public SearchFiltersComponent filter() {
        waitFor(searchFilters, visible);
        return new SearchFiltersComponent(searchFilters, this);
    }

    public SearchPage clickOnFiltersIcon() {
        filterIcon.click();
        return this;
    }

    public SearchPage verifyFiltersIsNotDisplayed() {
        waitFor(searchFilters, not(visible));
        return this;
    }

    public SearchPage verifyFiltersIsDisplayed() {
        waitFor(searchFilters, visible);
        return this;
    }
}
