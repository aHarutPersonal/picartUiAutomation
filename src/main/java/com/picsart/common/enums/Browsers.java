package com.picsart.common.enums;

public enum Browsers {
    CHROME,
    FIREFOX,
    EDGE,
    SAFARI,
    OPERA,
    IE;

    public static Browsers fromString(String browser) {
        return Browsers.valueOf(browser.toUpperCase());
    }
}
