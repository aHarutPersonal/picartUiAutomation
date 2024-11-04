package com.picsart.pages;

import static com.picsart.common.interactions.JSDriver.getCanvasData;
import static com.picsart.wait.WaitCondition.visible;

import com.picsart.base.BasePage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditorPage extends BasePage<EditorPage> {

    @FindBy(css = "canvas")
    private WebElement canvas;

    public EditorPage() {
        super("create/editor");
    }

    @Override
    protected void waitForPageToLoad() {
        waitFor(canvas, visible);
    }

    public String getCanvasImage() {
        return getCanvasData(canvas);
    }
}
