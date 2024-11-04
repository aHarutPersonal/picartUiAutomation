import static com.picsart.components.SearchFiltersComponent.LicenseFilters.PERSONAL;

import com.picsart.pages.SearchPage;
import org.assertj.core.api.Assertions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class SearchImagesPageTest extends BaseTest {
    private SearchPage searchPage;

    @BeforeClass
    public void setupPage() {
        searchPage = new SearchPage().get();
        searchPage.refresh().acceptCookies();
    }

    @Test
    public void testScenario1() {
        // Verify filter appear and disappear functionality
        searchPage
            .clickOnFiltersIcon()
            .verifyFiltersIsNotDisplayed()
            .clickOnFiltersIcon()
            .verifyFiltersIsDisplayed()
            // Select filter
            .filter()
            .selectFilter(PERSONAL);
        // Verify that the selected filter is applied
        searchPage.waitForSearchResultsUpdated().getSearchResults().forEach(searchResultComponent -> {
            searchResultComponent.hover();
            Assertions.assertThat(searchResultComponent.isLikeButtonDisplayed()).isTrue();
            Assertions.assertThat(searchResultComponent.isSaveButtonDisplayed()).isTrue();
            Assertions.assertThat(searchResultComponent.isTryNowButtonDisplayed()).isTrue();
            Assertions.assertThat(searchResultComponent.isPremiumIconDisplayed()).isFalse();
        });
        // Click on the like button of the first search result
        searchPage.getSearchResults().get(0).hover().clickOnLikeButton();
        // Verify that sign in popup is displayed
        searchPage.getRegistrationOverlay()
                  .verifyIsDisplayed()
                  .close()
                  // Clear all filters
                  .filter()
                  .clearAllFilters();
        // Verify that the selected filter is cleared and premium search results are displayed
        var premiumSearchResult = searchPage
            .waitForPremiumSearchResults()
            .getPremiumSearchResults()
            .stream()
            .findAny()
            .orElseThrow(() -> new AssertionError("No premium search results found"));

        // Verify that the premium search result has no like and save buttons and has a try now button
        premiumSearchResult.hover();
        Assertions.assertThat(premiumSearchResult.isLikeButtonDisplayed()).isFalse();
        Assertions.assertThat(premiumSearchResult.isSaveButtonDisplayed()).isFalse();
        Assertions.assertThat(premiumSearchResult.isTryNowButtonDisplayed()).isTrue();

        // Click on the try now button and verify that the canvas image is not blank
        Assertions.assertThat(premiumSearchResult.clickOnTryNowButton().getCanvasImage()).isNotBlank();
    }
}
