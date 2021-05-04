package no.kristiania.frontend.selenium.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class CreateMoviePO {

    private final WebDriver driver;

    public CreateMoviePO(WebDriver driver) {
        this.driver = driver;
    }

    public void enterMovieCredentials(String title, String director, String summary) {
        WebElement t = driver.findElement(By.id("title"));
        WebElement d = driver.findElement(By.id("director"));
        WebElement s = driver.findElement(By.id("summary"));

        t.sendKeys(title);
        d.sendKeys(director);
        s.sendKeys(summary);
    }

    public void clickSubmit() {
        WebElement submit = driver.findElement(By.id("submit"));
        submit.click();
    }
}
