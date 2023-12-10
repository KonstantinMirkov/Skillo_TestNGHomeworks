package HomeworkLection14;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

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
            this.driver.quit();
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
        // Open iskillo Home Page Class
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHome();
        homePage.isUrlLoaded();

        // Header Class
        Header headerMenu = new Header(driver);
        headerMenu.clickLogin();

        // Login Class
        LoginPage loginPage = new LoginPage(driver);
        loginPage.isUrlLoaded();

        // Get Sign in text
        String elemText = loginPage.getSignInElementText();
        Assert.assertEquals(elemText, "Sign in");

        // Fill in username
        loginPage.populateUsername(username);

        // Fill in password
        loginPage.populatePassword(password);

        // Click on Sign in
        loginPage.clickSignIn();
    }

    @Test(dataProvider = "getUsers")
    public void logoutFromLoggedInStateFromHome(String username, String password) {
        // Login to the application
        testLogin(username, password);

        // Click logout
        Header header = new Header(driver);
        header.clickLogout();

        // Verify that the user is logged out and redirected to the login page
        LoginPage loginPage = new LoginPage(driver);
        String elemText = loginPage.getSignInElementText();
        Assert.assertEquals(elemText, "Sign in");
    }

    @Test(dataProvider = "getUsers")
    public void logoutFromLoggedInStateFromProfile(String username, String password) {
        // Login to the application
        testLogin(username, password);

        // Navigate to the profile link and click
        Header header = new Header(driver);
        header.clickProfile();

        ProfilePage profilePage = new ProfilePage(driver);

        // Validate profile page URL
        profilePage.isUrlLoaded();

        // Click logout
        header.clickLogout();

        // Verify that the user is logged out and redirected to the login page
        LoginPage loginPage = new LoginPage(driver);
        String elemText = loginPage.getSignInElementText();
        Assert.assertEquals(elemText, "Sign in");
    }

    @Test(dataProvider = "getUsers")
    public void logoutFromLoggedInStateFromNewPost(String username, String password) {
        // Login to the application
        testLogin(username, password);

        // Navigate to the profile link and click
        Header header = new Header(driver);
        header.clickNewPost();

        // Validate new post page URL
        NewPostPage newPostPage = new NewPostPage(driver);
        newPostPage.isUrlLoaded();

        // Click logout
        header.clickLogout();

        // Verify that the user is logged out and redirected to the login page
        LoginPage loginPage = new LoginPage(driver);
        String elemText = loginPage.getSignInElementText();
        Assert.assertEquals(elemText, "Sign in");
    }

    @Test
    public void logoutFromLoggedOutState() {
        // Open iskillo Home page
        HomePage homePage = new HomePage(driver);
        homePage.navigateToHome();
        homePage.isUrlLoaded();

        // Validate page title is matching the expected page
        String pageTitle = driver.getTitle();
        Assert.assertEquals("ISkillo", pageTitle);

        // Ensure that no user is logged in by finding login button
        Header header = new Header(driver);
        header.isLoginLinkVisible();

        // Navigate to the logout button and throw exception when not found
        try {
            WebElement logoutButton = driver.findElement(By.xpath("//*[@class=\"fas fa-sign-out-alt fa-lg\"]"));
            Assert.fail("Logout button should not be present.");
        } catch (NoSuchElementException e) {
            System.out.println("There is no logout button.");
        }
    }
}
