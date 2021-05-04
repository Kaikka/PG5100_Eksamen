package no.kristiania.frontend.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MoviePO {

    private final WebDriver driver;

    public MoviePO(WebDriver driver) {
        this.driver = driver;
    }

    public void writeInReviewArea(String text) {
        WebElement review = driver.findElement(By.id("reviewInput"));
        review.sendKeys(text);
    }

    public void clickPublish() {
        WebElement submit = driver.findElement(By.id("publish"));
        submit.click();
    }



}
