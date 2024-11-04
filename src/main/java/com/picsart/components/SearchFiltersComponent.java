package com.picsart.components;

import static com.picsart.wait.WaitCondition.visible;

import java.util.List;

import com.picsart.base.BaseComponent;
import com.picsart.pages.SearchPage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SearchFiltersComponent extends BaseComponent<SearchFiltersComponent, SearchPage> {
    private static final String FILTER_CHECKBOX_BY_NAME = ".//li[@data-testid='checkbox-item-root' and .//label/text()='%s']//input";

    @FindBy(xpath = ".//div[@data-testid='search-filter-header-root']//button[@data-testid='search-filter-header-item']")
    private List<WebElement> selectedFilters;

    public SearchFiltersComponent(WebElement searchContext, SearchPage page) {
        super(searchContext, page);
    }

    public SearchFiltersComponent selectFilter(LicenseFilters filter) {
        By filterCheckbox = By.xpath(FILTER_CHECKBOX_BY_NAME.formatted(filter.getFilterName()));
        waitFor(filterCheckbox, visible);
        if (!base.findElement(filterCheckbox).isSelected()) {
            base.findElement(filterCheckbox).click();
        }
        return this;
    }

    public SearchFiltersComponent clearAllFilters() {
        clickOnButtonByName("Clear All");
        return this;
    }


    @Getter
    @RequiredArgsConstructor
    public enum LicenseFilters {
        ALL("All"),
        COMMERCIAL("Commercial"),
        PERSONAL("Personal");
        private final String filterName;
    }
}
