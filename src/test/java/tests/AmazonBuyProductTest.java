package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AmazonHomePage;
import pages.ProductPage;
import pages.CartPage;

public class AmazonBuyProductTest extends BaseTest {

    @Test
    public void buyProductTest() throws InterruptedException {

        AmazonHomePage home = new AmazonHomePage(driver);
        ProductPage product = new ProductPage(driver);
        CartPage cart = new CartPage(driver);

        // Search product
        home.searchProduct("iPhone 15");

        Thread.sleep(3000);

        // Click first result
        home.selectFirstProduct();

        // Switch tab
        product.switchToNewTab();

        Thread.sleep(3000);

        // Add to cart
        product.clickAddToCart();

        Thread.sleep(3000);

        // Validate cart count
        String count = cart.getCartCount();

        Assert.assertEquals(count, "1", "Product not added to cart!");

        System.out.println("Product successfully added to cart");
    }
}
