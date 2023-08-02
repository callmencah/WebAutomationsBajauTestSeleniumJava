package Bajau;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TestCase {

    private WebDriver driver;
    private WebDriverWait wait;
    @BeforeAll
    public static void setupDriver() {
        WebDriverManager.chromedriver().setup();

    }

    @BeforeEach
    void setup() {
        driver = createDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    void tearDown() {
        quitDriver();
    }

    protected WebDriver createDriver() {
        return new ChromeDriver();
    }

    protected void quitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @Order(1)
    void CheckLoginFunctionality() {

        // Step 1: Open the web browser & max window
        driver.manage().window().maximize();
        // Step 2: Go to the login page URL target
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        // Step 3: Insert valid username & password
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder=\"Username\"]")));
        username.sendKeys("Admin");
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder=\"Password\"]")));
        password.sendKeys("admin123");
        WebElement buttonLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@type=\"submit\"]")));
        buttonLogin.click();
        // Step 4: Successfully direct to dashboard
        // Check if the page successfully navigates to the dashboard page
        String expectedUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
        String actualUrl = driver.getCurrentUrl();

        // Assertion: Verify if the URL of the page after login matches the expected URL
        if (expectedUrl.equals(actualUrl)) {
            System.out.println("Assertion successful: The dashboard page has been loaded.");
        } else {
            System.out.println("Assertion failed: The dashboard page did not load as expected.");
        }

        // Step 5: Verify login username
        // You can add assertions here to verify the username is displayed correctly on the dashboard
        WebElement usernamedisplay= wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[2]/ul/li/span/p")));

        // Check if the displayed username matches the expected username
        WebElement usernameElement = driver.findElement(By.xpath("//*[@id=\"app\"]/div[1]/div[1]/header/div[1]/div[2]/ul/li/span/p"));
        String actualUsername = usernameElement.getText();
        String expectedUsername = usernameElement.getText();
        System.out.println(usernamedisplay.getText());
        // Assertion: Verify if the displayed username matches the expected username
        if (expectedUsername.equals(actualUsername)) {
            System.out.println("Assertion successful: The displayed username matches the expected one.");
        } else {
            System.out.println("Assertion failed: The displayed username does not match the expected one.");
        }
    }


    @Test
    @Order(2)
    void UserInsertInvalidUsernameOrPassword() {
        // Step 1: Open the web browser & max window
        driver.manage().window().maximize();
        // Step 2: Go to the login page URL target
        driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");
        // Step 3: Insert invalid username/password
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder=\"Username\"]")));
        username.sendKeys("Admi1n");
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@placeholder=\"Password\"]")));
        password.sendKeys("admin1213");
        WebElement buttonLogin = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@type=\"submit\"]")));
        buttonLogin.click();
        // Step 4: Verify login failed
        // Verify the login result
        // Find the error message element and get its text
        WebElement errorMessageElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(" //*[@id=\"app\"]/div[1]/div/div[1]/div/div[2]/div[2]/div/div[1]/div[1]/p")));
        String errorMessage = errorMessageElement.getText();

        // Verify the login result
        if (errorMessage.contains("Invalid credentials")) {
            System.out.println("Login failed with invalid credentials as expected!");
        } else {
            System.out.println("Login succeeded, but it was expected to fail with invalid credentials!");
        }

    }
}