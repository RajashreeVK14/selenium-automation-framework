package base;

import config.ConfigReader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {

    protected WebDriver driver;
    protected ConfigReader config;
    @BeforeMethod
    public void setup() {
    	System.out.println("inside setup");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        config = new ConfigReader(); 
        driver.manage().window().maximize();
        driver.get(config.get("baseUrl"));
    	System.out.println("before printing url");
        System.out.println(config.get("url"));
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }
}
