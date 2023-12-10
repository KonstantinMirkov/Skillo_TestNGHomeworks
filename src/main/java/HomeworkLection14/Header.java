package HomeworkLection14;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Header {
    private final WebDriver driver;
    @FindBy(id = "nav-link-profile")
    private WebElement profileLink;
    @FindBy(id = "nav-link-new-post")
    private WebElement newPostLink;
    @FindBy(id = "nav-link-login")
    private WebElement loginLink;
    @FindBy(xpath = "//*[@class=\"fas fa-sign-out-alt fa-lg\"]")
    private WebElement logoutLink;

    public Header(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void clickProfile() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
        wait.until(ExpectedConditions.elementToBeClickable(profileLink));
        profileLink.click();
    }

    public void clickNewPost() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));
        wait.until(ExpectedConditions.elementToBeClickable(newPostLink));
        newPostLink.click();
    }

    public void clickLogin() {
        loginLink.click();
    }

    public void clickLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutLink.click();
    }

    public void isLoginLinkVisible() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.visibilityOf(loginLink));
    }
}
