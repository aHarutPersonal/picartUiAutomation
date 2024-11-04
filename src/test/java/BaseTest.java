import static com.picsart.common.PropertiesManager.setProperty;
import static com.picsart.common.enums.PropertyNames.BROWSER;
import static com.picsart.common.enums.PropertyNames.BROWSER_SIZE;
import static com.picsart.common.enums.PropertyNames.URL;

import com.picsart.common.DriverProvider;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class BaseTest {

    @Parameters({"browser", "url", "browserSize"})
    @BeforeTest
    public void setUp(String browser, String url, String browserSize) {
        setProperty(BROWSER.name(), browser);
        setProperty(URL.name(), url);
        setProperty(BROWSER_SIZE.name(), browserSize);
    }

    @AfterTest(alwaysRun = true)
    public void tearDown() {
        DriverProvider.quitDriver();

    }
}
