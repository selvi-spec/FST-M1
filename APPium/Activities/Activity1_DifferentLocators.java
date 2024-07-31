package Activities;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class Activity1_DifferentLocators {

    AndroidDriver driver;
    //IOSDriver driver;

    @BeforeClass
    public void setUp() throws MalformedURLException {
        // XCUITestOptions caps = new XCUITestOptions(); --> For iOS
        //Set the capabilities for Android
        UiAutomator2Options caps = new UiAutomator2Options()
                .setPlatformName("android")
                .setAutomationName("UiAutomator2")
//                .setApp("path/to/app.ipa") --> Generic App for Android and iOS
                .setAppPackage("com.sec.android.app.popupcalculator")
                .setAppActivity(".Calculator")
                .noReset();
        //Set the serverURL
        URL serverURL = new URL("http://localhost:4723/wd/hub");
        //Initialize the Android Driver
        driver = new AndroidDriver(serverURL, caps);
    }

    @Test
    public void multiplyTest(){
        //Find the Number 7 and tap it
        driver.findElement(AppiumBy.id("calc_keypad_btn_07")).click();
        //Find the Multiply button and tap it
        driver.findElement(AppiumBy.xpath("//android.widget.Button[@content-desc=\"Multiplication\"]")).click();
        //Find the Number 6 and tap it
        driver.findElement(AppiumBy.id("calc_keypad_btn_06")).click();
        //Find the Multiply button and tap it
        driver.findElement(AppiumBy.accessibilityId("Multiplication")).click();
        //Find the Number 10 and tap it
        driver.findElement(AppiumBy.id("calc_keypad_btn_01")).click();
        driver.findElement(AppiumBy.xpath("//android.widget.Button[@content-desc=\"0\"]")).click();
        //Find the Equals button and tap it
        driver.findElement(AppiumBy.accessibilityId("Calculation")).click();
        //Get the Calculated result
        String calculatedResult = driver.findElement(AppiumBy.id("calc_edt_formula")).getText();
        //Assertion
        Assert.assertEquals(calculatedResult, "420 Calculation result");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }

}
