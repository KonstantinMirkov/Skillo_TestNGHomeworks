package HomeworkLection14;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.time.Duration;

public class LogoutTests {
    private WebDriver driver;

    @BeforeSuite
    protected final void setupTestSuite() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    protected final void setUpMethod() {
        this.driver = new ChromeDriver();
        this.driver.manage().window().maximize();
    }

    @AfterMethod
    protected final void tearDownTest() {
        if (this.driver != null) {
           this.driver.close();
        }
    }

    @DataProvider(name = "getUsers")
    public Object[][] getUsers(){
        return new Object[][] {
                {"imeil@abv.bg","Parola12345"}
        };
    }

    @Test(dataProvider = "getUsers")
    public void testLogin(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));

        // Open iskillo page
        driver.get("http://training.skillo-bg.com:4300/posts/all");
        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/posts/all"));

        //validate page title is matching the expected page
        String pageTitle = driver.getTitle();
        Assert.assertEquals("ISkillo", pageTitle);

        //Click on Login link
        WebElement loginLink = driver.findElement(By.id("nav-link-login"));
        wait.until(ExpectedConditions.visibilityOf(loginLink));
        loginLink.click();

        //Validate login page URL
        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/users/login"));

        //Validate Sign in form is visible
        WebElement signInElement = driver.findElement(By.xpath("//p[text()='Sign in']"));
        wait.until(ExpectedConditions.visibilityOf(signInElement));

        //Enter valid email address
        WebElement userNameField = driver.findElement(By.id("defaultLoginFormUsername"));
        userNameField.sendKeys(username);

        //Enter valid password
        WebElement passwordField = driver.findElement(By.id("defaultLoginFormPassword"));
        passwordField.sendKeys(password);

        //Click on Remember me button
        WebElement rememberMeCheckbox = driver.findElement(By.xpath("//*[@formcontrolname=\"rememberMe\"]"));
        rememberMeCheckbox.click();

        //Validate Remember me is checked
        Assert.assertTrue(rememberMeCheckbox.isSelected(), "The Remember me checkbox is not selected");

        //Check Sign in button is enabled
        WebElement signInButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("sign-in-button")));

        //Click on Sign in button
        signInButton.click();
    }

    @Test(dataProvider = "getUsers")
    public void logoutFromLoggedInStateFromHome(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));

        // Login to the application
        testLogin(username, password);

        // Navigate to the logout button and click
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navbarColor01\"]/ul[2]/li/a/i")));
        logoutButton.click();

        // Verify that the user is logged out and redirected to the login page
        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/users/login"));
        WebElement signInElement = driver.findElement(By.xpath("//p[text()='Sign in']"));
        Assert.assertEquals("Sign in", signInElement.getText());
    }

    @Test(dataProvider = "getUsers")
    public void logoutFromLoggedInStateFromProfile(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

        // Login to the application
        testLogin(username, password);

        // Navigate to the profile link and click
        WebElement profileLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-link-profile")));
        profileLink.click();

        //Validate profile page URL
        wait.until(ExpectedConditions.urlContains("http://training.skillo-bg.com:4300/users/"));

        // Navigate to the logout button and click
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navbarColor01\"]/ul[2]/li/a/i")));
        logoutButton.click();

        // Verify that the user is logged out and redirected to the login page
        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/users/login"));
        WebElement signInElement = driver.findElement(By.xpath("//p[text()='Sign in']"));
        Assert.assertEquals("Sign in", signInElement.getText());
    }

    @Test(dataProvider = "getUsers")
    public void logoutFromLoggedInStateFromNewPost(String username, String password) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(90));

        // Login to the application
        testLogin(username, password);

        // Navigate to the profile link and click
        WebElement newPostLink = wait.until(ExpectedConditions.elementToBeClickable(By.id("nav-link-new-post")));
        newPostLink.click();

        wait.until(ExpectedConditions.urlContains("http://training.skillo-bg.com:4300/posts/create"));

        // Navigate to the logout button and click
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navbarColor01\"]/ul[2]/li/a/i")));
        logoutButton.click();

        // Verify that the user is logged out and redirected to the login page
        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/users/login"));
        WebElement signInElement = driver.findElement(By.xpath("//p[text()='Sign in']"));
        Assert.assertEquals("Sign in", signInElement.getText());
    }

    @Test
    public void logoutFromLoggedOutState() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        // Open iskillo page
        driver.get("http://training.skillo-bg.com:4300/posts/all");
        wait.until(ExpectedConditions.urlToBe("http://training.skillo-bg.com:4300/posts/all"));

        //validate page title is matching the expected page
        String pageTitle = driver.getTitle();
        Assert.assertEquals("ISkillo", pageTitle);

        // Ensure that no user is logged in by finding login button
        WebElement loginLink = driver.findElement(By.id("nav-link-login"));
        wait.until(ExpectedConditions.visibilityOf(loginLink));

        // Navigate to the logout button and throw exception when not found
        try {
            WebElement logoutButton = driver.findElement(By.xpath("//*[@id=\"navbarColor01\"]/ul[2]/li/a/i"));
            Assert.fail("Logout button should not be present.");
        } catch (NoSuchElementException e) {
            System.out.println("There is no logout button.");
        }
    }
}
