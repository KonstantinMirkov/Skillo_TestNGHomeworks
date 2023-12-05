package HomeworkLection15;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class UrlLoader {
    private final WebDriver driver;

    public UrlLoader(WebDriver driver) {
        this.driver = driver;
    }

    public void loadUrl (String url) {
        driver.get(url);
    }

    public boolean isUrlLoaded(String url) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));
        return wait.until(ExpectedConditions.urlToBe(url));
    }
}
