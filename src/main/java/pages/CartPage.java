package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class CartPage {

    WebDriver driver;

    By cartCount = By.id("nav-cart-count");

    public CartPage(WebDriver driver) {
        this.driver = driver;
    }

    public String getCartCount() {
        return driver.findElement(cartCount).getText();
    }
}
