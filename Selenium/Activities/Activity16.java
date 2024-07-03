import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Activity16 {
    public static void main(String[] args) {
        // Set up Firefox driver
        WebDriverManager.firefoxdriver().setup();
        // Create a new instance of the Firefox driver
        WebDriver driver = new FirefoxDriver();
        // Create the Wait object
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Open the page
        driver.get("https://v1.training-support.net/selenium/dynamic-attributes");
        // Print the title of the page
        System.out.println("Home page title: " + driver.getTitle());

        // Find the fields of the sign-up form
        WebElement userName = driver.findElement(By.xpath("//input[contains(@class, '-username')]"));
        WebElement password = driver.findElement(By.xpath("//input[contains(@class, '-password')]"));
        WebElement confirmPassword = driver.findElement(By.xpath("//label[text() = 'Confirm Password']/following-sibling::input"));
        WebElement email = driver.findElement(By.xpath("//label[contains(text(), 'mail')]/following-sibling::input"));

        // Type credentials
        userName.sendKeys("NewUser");
        password.sendKeys("Password");
        confirmPassword.sendKeys("Password");
        email.sendKeys("real_email@xyz.com");
        // Click Sign Up
        driver.findElement(By.xpath("//button[contains(text(), 'Sign Up')]")).click();

        // Print login message
        String loginMessage = driver.findElement(By.id("action-confirmation")).getText();
        System.out.println("Login message: " + loginMessage);

        // Close the browser
        driver.quit();
    }
}

Activity 16
Python Solution:

        # Set up the Firefox Driver with WebDriverManger
        service = FirefoxService(GeckoDriverManager().install())

# Start the Driver
with webdriver.Firefox(service=service) as driver:
        # Initialize the wait object
wait = WebDriverWait(driver, 10)

    # Navigate to the URL
    driver.get("https://v1.training-support.net/selenium/dynamic-attributes")
    # Print the title of the page
print("Page title is: ", driver.title)

    # Find the username field
username = driver.find_element(By.XPATH, "//input[contains(@class, '-username')]")
    # Find the password field
password = driver.find_element(By.XPATH, "//input[contains(@class, '-password')]")
    # Find the confirm password field
        confirm_password = driver.find_element(By.XPATH, "//label[contains(text(), 'Confirm Password')]//following::input")
    # Alternatively, following-sibling can also be used
    # confirm_password = driver.find_element_by_xpath("//label[text()='Confirm Password')]/following-sibling::input")
    # Find the email field
email = driver.find_element(By.XPATH, "//input[contains(@class, 'email-')]")

    # Enter the credentials
    username.send_keys("Deku")
    password.send_keys("PlusUltra!")
    confirm_password.send_keys("PlusUltra!")
    email.send_keys("deku@ua.edu")
    # Find the Sign Up button and click it
    driver.find_element(By.XPATH, "//button[text()='Sign Up']").click()

    # Wait for the login message to appear
    wait.until(EC.visibility_of_element_located((By.ID, "action-confirmation")))
        # Print the success message
message = driver.find_element(By.ID, "action-confirmation").text
print("Success message: ", message)

Session 2
Handling Selects
41 . 2



