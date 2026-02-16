package tests;

import base.BaseTest;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.GooglePage;

public class GoogleTest extends BaseTest {

    @Test
    public void verifyGoogleHomePage() {

        GooglePage googlePage = new GooglePage(driver);

        // Assertion 1 - Validate Title
        String actualTitle = googlePage.getPageTitle();
        Assert.assertEquals(actualTitle, "Google", 
                "Page title is not matching!");

        // Assertion 2 - Validate Search Box Displayed
        Assert.assertTrue(googlePage.isSearchBoxDisplayed(), 
                "Search box is not displayed!");

        System.out.println("Test Passed Successfully");
    }
}
