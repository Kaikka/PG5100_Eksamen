package no.kristiania.frontend.selenium;

import no.kristiania.frontend.selenium.ui.*;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
public class SeleniumTest {

    @LocalServerPort
    private int port;


    @Test
    public void testNotLoggedIn() {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        try {
            driver.get("http://localhost:" + port);

            assertThat(driver.getPageSource(), containsString("You are not logged in"));
        } finally {
            driver.close();
        }
    }

    @Test
    public void testDefaultMovies() {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();

        try {
            driver.get("http://localhost:" + port);
            assertThat(driver.getPageSource(), containsString("Ex Machina"));
            assertThat(driver.getPageSource(), containsString("Cloud Atlas"));
        } finally {
            driver.close();
        }
    }

    @Test
    public void testCreateUserAndLogOut() throws InterruptedException {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        SignUpPageObject signUpObject = new SignUpPageObject(driver);
        IndexPO indexPO = new IndexPO(driver);

        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("http://localhost:" + port);
            indexPO.clickSignUpButton();
            Thread.sleep(500);
            assertThat(driver.getTitle(), containsString("Sign Up"));
            Thread.sleep(500);
            signUpObject.enterCredentials("test", "hunter12");
            signUpObject.clickSubmit();
            Thread.sleep(500);
            assertThat(driver.getPageSource(), containsString("Welcome test"));
            indexPO.clickLogOutButton();
            Thread.sleep(500);
            assertThat(driver.getPageSource(), containsString("You are not logged in"));

        } finally {
            driver.close();
        }
    }

    @Test
    public void testLoginAndCreateAMovie() throws InterruptedException {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        LogInPO logInPO = new LogInPO(driver);
        CreateMoviePO createMoviePO = new CreateMoviePO(driver);
        IndexPO indexPO = new IndexPO(driver);
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("http://localhost:" + port);
            indexPO.clickLogInButton();
            Thread.sleep(500);
            logInPO.enterCredentials("foo", "123");
            logInPO.clickSubmit();
            Thread.sleep(500);
            assertThat(driver.getPageSource(), containsString("Welcome foo"));
            indexPO.clickCreateMovieButton();
            Thread.sleep(500);
            assertThat(driver.getTitle(), containsString("Create movie"));
            createMoviePO.enterMovieCredentials("Foomovie", "Foodirector", "Foosummary");
            createMoviePO.clickSubmit();
            Thread.sleep(500);
            assertThat(driver.getPageSource(), containsString("Foomovie"));


        } finally {
            driver.close();
        }
    }

    @Test
    public void testWriteReview() throws InterruptedException {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        LogInPO logInPO = new LogInPO(driver);
        MoviePO moviePO = new MoviePO(driver);
        IndexPO indexPO = new IndexPO(driver);

        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("http://localhost:" + port);

            indexPO.clickMovieDetails("1");
            Thread.sleep(500);
            assertThat(driver.getPageSource(), containsString("Login to write a review"));
            moviePO.clickGoBack();
            Thread.sleep(500);
            indexPO.clickLogInButton();
            Thread.sleep(500);
            logInPO.enterCredentials("bar", "123");
            logInPO.clickSubmit();
            Thread.sleep(500);
            assertThat(driver.getPageSource(), containsString("Welcome bar"));
            indexPO.clickMovieDetails("1");
            Thread.sleep(500);
            moviePO.writeInReviewArea("Test review");
            moviePO.clickPublish();

        } finally {
            driver.close();
        }
    }

    @Test
    public void testStars() throws InterruptedException {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        LogInPO logInPO = new LogInPO(driver);
        MoviePO moviePO = new MoviePO(driver);
        IndexPO indexPO = new IndexPO(driver);

        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("http://localhost:" + port);

            String rating = "5.0";
            assertThat(driver.findElement(By.id("MovieRatingID_1")).getText(), containsString(rating));
            Thread.sleep(500);
            indexPO.clickLogInButton();
            Thread.sleep(500);
            logInPO.enterCredentials("bar", "123");
            logInPO.clickSubmit();
            Thread.sleep(500);
            assertThat(driver.getPageSource(), containsString("Welcome bar"));
            indexPO.clickMovieDetails("1");
            Thread.sleep(500);
            moviePO.writeInReviewArea("Test review");
            moviePO.clickPublish();
            Thread.sleep(500);
            moviePO.clickGoBack();
            Thread.sleep(500);
            String newRating = "3.0";
            assertThat(driver.findElement(By.id("MovieRatingID_1")).getText(), containsString(newRating));

        } finally {
            driver.close();
        }
    }


    @Test
    public void testSorting() throws InterruptedException {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        LogInPO logInPO = new LogInPO(driver);
        MoviePO moviePO = new MoviePO(driver);
        IndexPO indexPO = new IndexPO(driver);

        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("http://localhost:" + port);

            indexPO.clickLogInButton();
            Thread.sleep(500);
            logInPO.enterCredentials("fjong", "123");
            logInPO.clickSubmit();
            Thread.sleep(500);
            assertThat(driver.getPageSource(), containsString("Welcome fjong"));
            indexPO.clickMovieDetails("4");
            Thread.sleep(500);
            String newReview = "Test review";
            moviePO.writeInReviewArea(newReview);
            moviePO.clickPublish();
            Thread.sleep(500);
            String top = "I think therefore am I?";
            assertThat(driver.findElement(By.xpath("//*[@class='review-container']//child::div[1]//child::p[1]")).getText(), containsString(top));
            Thread.sleep(500);
            moviePO.clickSortByDate();
            Thread.sleep(500);
            assertThat(driver.findElement(By.xpath("//*[@class='review-container']//child::div[1]//child::p[1]")).getText(), containsString(newReview));

        } finally {
            driver.close();
        }
    }



}
