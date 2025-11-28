import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class Day30_Selenium {

    WebDriver driver;

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        options.addArguments("--disable-features=PasswordManagerOnboarding");
        options.addArguments("--disable-features=PasswordLeakDetection");
        options.addArguments("--disable-features=Password generation");
        options.addArguments("--disable-features=AutofillServerCommunication");
        options.addArguments("--disable-features=CredentialManager");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--disable-features=EnableEphemeralFlashPermission");
        options.addArguments("--incognito");
        options.addArguments("--disable-autofill-keyboard-accessory-view");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--disable-extensions");

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Test
    void testSuccessCheckout() throws InterruptedException {
        Dotenv dotenv = Dotenv.load();

        String baseUrl = dotenv.get("BASE_URL");
        String username = dotenv.get("USERNAME");
        String password = dotenv.get("PASSWORD");

        // 1. Open website
        driver.get(baseUrl);
        Thread.sleep(1000);

        // 2. Login
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.id("login-button")).click();
        Thread.sleep(1000);

        // 3. Add Sauce Labs Backpack
        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        Thread.sleep(1000);

        // 4. Open cart
        driver.findElement(By.className("shopping_cart_link")).click();
        Thread.sleep(1000);

        // 5. Checkout
        driver.findElement(By.id("checkout")).click();
        Thread.sleep(1000);

        // 6. Fill form
        driver.findElement(By.id("first-name")).sendKeys("Feb");
        driver.findElement(By.id("last-name")).sendKeys("syahp");
        driver.findElement(By.id("postal-code")).sendKeys("61111");
        Thread.sleep(1000);

        // 7. Continue
        driver.findElement(By.id("continue")).click();
        Thread.sleep(1000);

        // 8. Finish checkout
        driver.findElement(By.id("finish")).click();
        Thread.sleep(1000);

        // 9. Assertion
        String successMessage = driver.findElement(By.className("complete-header")).getText();
        assertEquals("Thank you for your order!", successMessage);
    }

    @AfterEach
    void tearDown() throws InterruptedException {
        Thread.sleep(1500);
        driver.quit();
    }
}
