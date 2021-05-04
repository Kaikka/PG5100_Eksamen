package no.kristiania.frontend.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class IndexPO {

    private final WebDriver driver;

    public IndexPO(WebDriver driver) {
        this.driver = driver;
    }

    public void clickSignUpButton() {
        WebElement signupbutton = driver.findElement(By.id("linkToSignupId"));
        signupbutton.click();
    }

    public void clickLogInButton() {
        WebElement loginbutton = driver.findElement(By.id("linkToLoginId"));
        loginbutton.click();
    }

    public void clickLogOutButton() {
        WebElement logout = driver.findElement(By.id("logoutId"));
        logout.click();
    }

    public void clickCreateMovieButton() {
        WebElement create = driver.findElement(By.id("linkToCreateMovie"));
        create.click();
    }

    public void clickMovieDetails(String id) {
        WebElement movie = driver.findElement(By.id(id));
        movie.click();
    }
}
