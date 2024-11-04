package com.picsart.common;

import static com.picsart.common.PropertiesManager.getProperty;

import com.picsart.common.enums.Browsers;
import com.picsart.common.enums.PropertyNames;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverProvider {
    private static final ThreadLocal<WebDriver> WEB_DRIVER_THREAD_LOCAL = new ThreadLocal<>();

    public static WebDriver getDriver() {
        if (WEB_DRIVER_THREAD_LOCAL.get() == null) {
            initDriver(Browsers.fromString(getProperty(PropertyNames.BROWSER.name(), Browsers.CHROME.name())));
        }
        return WEB_DRIVER_THREAD_LOCAL.get();
    }

    private static void initDriver(Browsers browser) {
        WebDriver driver;
        switch (browser) {
            case CHROME -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case FIREFOX -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            default -> throw new IllegalArgumentException("Unsupported browser: " + browser);
        }
        setDriverSize(driver);
        WEB_DRIVER_THREAD_LOCAL.set(driver);
    }

    public static void quitDriver() {
        WebDriver driver = WEB_DRIVER_THREAD_LOCAL.get();
        if (driver != null) {
            driver.quit();
            WEB_DRIVER_THREAD_LOCAL.remove();
        }
    }

    private static void setDriverSize(WebDriver driver) {
        String[] browserSize = getProperty(PropertyNames.BROWSER_SIZE.name()).split("x");
        int width = Integer.parseInt(browserSize[0]);
        int height = Integer.parseInt(browserSize[1]);
        driver.manage().window().setSize(new Dimension(width, height));
    }
}
