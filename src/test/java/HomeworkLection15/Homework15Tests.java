package HomeworkLection15;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.http.HttpResponse;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Homework15Tests {
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

    @Test
    public void testAddRemoveElements() {
        String url = "http://the-internet.herokuapp.com/add_remove_elements/";
        UrlLoader urlLoader = new UrlLoader(driver);
        urlLoader.loadUrl(url);
        urlLoader.isUrlLoaded(url);

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(12));

        WebElement addButton = driver.findElement(By.xpath("//div[@class=\"example\"]/button"));
        addButton.click();

        WebElement deleteButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@id=\"elements\"]/button")));
        deleteButton.click();
    }

    @Test
    public void testBasicAuth() {
        UrlLoader urlLoader = new UrlLoader(driver);
        String authUrl = "http://admin:admin@the-internet.herokuapp.com/basic_auth/";
        urlLoader.loadUrl(authUrl);
        String url = "http://the-internet.herokuapp.com/basic_auth";
        urlLoader.loadUrl(url);
        urlLoader.isUrlLoaded(url);

        String pageMessage = driver.findElement(By.cssSelector("p")).getText();
        Assert.assertEquals(pageMessage, "Congratulations! You must have the proper credentials.");
    }

    @Test
    public void testBrokenImages() {
        String url = "http://the-internet.herokuapp.com/broken_images";
        UrlLoader urlLoader = new UrlLoader(driver);
        urlLoader.loadUrl(url);
        urlLoader.isUrlLoaded(url);

        driver.findElement(By.xpath("//*[contains(text(),\"Broken Images\")]")).click();

        List<WebElement> imageElements = driver.findElements(By.cssSelector("div.example img"));

        int imagesCount = 1;
        for (WebElement image : imageElements) {
            CloseableHttpClient httpClient = HttpClientBuilder.create().build();
            HttpGet getRequest = new HttpGet(image.getAttribute("src"));
            HttpResponse response;
            try {
                response = httpClient.execute(getRequest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            if (response.getCode() == 200) {
                imagesCount++;
            }
        }

        System.out.println("Broken images count: " + imagesCount);
    }

    @Test
    public void testCheckBoxes() {
        String url = "https://the-internet.herokuapp.com/checkboxes";
        UrlLoader urlLoader = new UrlLoader(driver);
        urlLoader.loadUrl(url);
        urlLoader.isUrlLoaded(url);

        driver.findElement(By.xpath("//*[contains(text(),\"Checkboxes\")]")).click();
        List<WebElement> checkboxElements = driver.findElements(By.cssSelector("#checkboxes input[type=\"checkbox\"]"));
        System.out.println("The number of checkboxes are : " + checkboxElements.size());
        for (WebElement checkbox : checkboxElements) {
            if (!checkbox.isSelected()) {
                checkbox.click();
            }
        }
    }

    @Test
    public void testDragAndDrop() {
        String url = "http://the-internet.herokuapp.com/drag_and_drop";
        UrlLoader urlLoader = new UrlLoader(driver);
        urlLoader.loadUrl(url);
        urlLoader.isUrlLoaded(url);


        driver.findElement(By.xpath("//*[contains(text(),\"Drag and Drop\")]")).click();
        WebElement elementA = driver.findElement(By.cssSelector("div#column-a"));
        System.out.println(elementA.getAttribute("id"));
        WebElement elementB = driver.findElement(By.cssSelector("div#column-b"));
        Actions actions = new Actions(driver);
        actions.dragAndDrop(elementA, elementB).build().perform();
    }

    @Test
    public void testDropdown() {
        String url = "http://the-internet.herokuapp.com/dropdown";
        UrlLoader urlLoader = new UrlLoader(driver);
        urlLoader.loadUrl(url);
        urlLoader.isUrlLoaded(url);

        driver.findElement(By.xpath("//*[contains(text(),\"Dropdown\")]")).click();
        WebElement dropdown = driver.findElement(By.id("dropdown"));
        Select select = new Select(dropdown);
        select.selectByValue("2");
    }

    @Test
    public void testDynamicLoading() {
        String url = "http://the-internet.herokuapp.com/dynamic_loading";
        UrlLoader urlLoader = new UrlLoader(driver);
        urlLoader.loadUrl(url);
        urlLoader.isUrlLoaded(url);

        driver.findElement(By.xpath("//*[contains(text(),\"Dynamically Loaded\")]")).click();
        List<WebElement> dynamicContent = driver.findElements(By.xpath("//div[@class = \"row\"]"));

        System.out.println(dynamicContent.get(1).findElement(By.cssSelector("div:nth-child(2)")).getText());
    }
}
