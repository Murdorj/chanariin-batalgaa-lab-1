package com.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.cdimascio.dotenv.Dotenv;

import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTest {
    private WebDriver driver;

    // 👉 Эндээс: class-ын түвшинд зарлая (scope OK)
    private static final String BASE_URL     = "https://student.must.edu.mn";
    private static final Dotenv dotenv = Dotenv.load();
    private static final String user = dotenv.get("MUST_USER");
    private static final String pass = dotenv.get("MUST_PASS");

    @BeforeEach
    void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
    }

    @Test
    void testLoginAndCheckStudentCode() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // 1) Нээх
        driver.get(BASE_URL);

        // 2) Username/Password талбар (олон locator-оос эхний таарсныг хэрэглэнэ)
        By userBy = By.cssSelector("input#UserName, input#username, input[name='UserName'], input[name='username']");
        By passBy = By.cssSelector("input#Password, input#password, input[name='Password'], input[name='password']");
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(userBy));
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(passBy));

        // 3) Login товч
        WebElement loginBtn = wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector("input[type='submit'][value='Нэвтрэх'], button[type='submit']")
        ));

        // 4) Нэвтрэх
        username.sendKeys(user);  // 👈 нэг л утгыг ашиглана
        password.sendKeys(pass);
        loginBtn.click();

        // 5) Modal/backdrop байвал хаах
        closeModalIfPresent(wait);

        // 6) Амжилттай login эсэх (жишээ: 'Хувийн мэдээлэл' линк харагдах)
        assertTrue(driver.getPageSource().contains("Хувийн мэдээлэл"),
                "Login амжилттай болсон байх ёстой");

        // 7) Оюутны кодыг UI-аас уншаад харьцуулах
        WebElement codeEl = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//div[@class='title' and normalize-space()='Оюутны код:']/following-sibling::div[contains(@class,'text')]")
        ));
        String actualCode = codeEl.getText().trim();
        assertEquals(user, actualCode, "Оюутны код зөв харагдах ёстой");
    }

    @AfterEach
    void tearDown() {
        try {
            // logout хийхийн өмнө modal байвал хаачихъя
            closeModalIfPresent(new WebDriverWait(driver, Duration.ofSeconds(5)));

            // Logout (span текстээр найдвартай олгоё)
            WebElement logout = driver.findElement(By.xpath("//span[normalize-space()='Гарах']/ancestor::a"));
            logout.click();

            // Logout дараа login талбар буцаж ирэхийг шалгах (сонголттой)
            new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.or(
                            ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#UserName, input#username")),
                            ExpectedConditions.textToBePresentInElementLocated(By.tagName("body"), "Нэвтрэх")
                    ));
        } catch (Exception ignore) {
        } finally {
            if (driver != null) driver.quit();
        }
    }

    /** Modal/backdrop байвал хаах (backdrop дээр JS click) */
    private void closeModalIfPresent(WebDriverWait wait) {
        try {
            WebElement backdrop = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("div.modal-backdrop")
            ));
            if (backdrop.isDisplayed()) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", backdrop);
                // backdrop арилтал багахан хүлээе
                wait.until(ExpectedConditions.invisibilityOf(backdrop));
            }
        } catch (TimeoutException | NoSuchElementException ignored) {
            // modal байхгүй бол зүгээр л алгасна
        }
    }
}
