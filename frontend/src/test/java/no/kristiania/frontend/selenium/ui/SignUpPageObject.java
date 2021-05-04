package no.kristiania.frontend.selenium.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class SignUpPageObject {

    private final WebDriver driver;

    public SignUpPageObject(WebDriver driver) {
        this.driver = driver;
    }



    public void clickSubmit() {
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
    }

    public void enterCredentials(String username, String password) {
        WebElement user = driver.findElement(By.id("username"));
        WebElement pass = driver.findElement(By.id("password"));

        user.sendKeys(username);
        pass.sendKeys(password);
    }


}
