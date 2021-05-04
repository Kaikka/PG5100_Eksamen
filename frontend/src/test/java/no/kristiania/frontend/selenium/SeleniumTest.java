package no.kristiania.frontend.selenium;

import org.junit.jupiter.api.Test;
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
    void testNotLoggedIn() {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        try {
            driver.get("http://localhost:" + port);

            assertThat(driver.getPageSource(), containsString("You are not logged in"));
        } finally {
            driver.close();
        }
    }

    @Test
    void testCreateUserAndLogOut() throws InterruptedException {
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


/*            signUpObject.enterCredentials("test", "hunter12");
            Thread.sleep(8000);
            assertThat(driver.getPageSource(), containsString("User and/or password are wrong."));// caused flakey test
            Thread.sleep(8000);*/
        } finally {
            driver.close();
        }
    }

    @Test
    void testLoginAndCreateAMovie() throws InterruptedException {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        SignUpPageObject signUpObject = new SignUpPageObject(driver);
        CreateMoviePO createMoviePO = new CreateMoviePO(driver);
        IndexPO indexPO = new IndexPO(driver);
        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("http://localhost:" + port);
            indexPO.clickLogInButton();
            Thread.sleep(500);
            signUpObject.enterCredentials("foo", "123");
            signUpObject.clickSubmit();
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
    void testLoginAndWriteReview() throws InterruptedException {
        WebDriver driver = SeleniumDriverHandler.getChromeDriver();
        SignUpPageObject signUpObject = new SignUpPageObject(driver);
        CreateMoviePO createMoviePO = new CreateMoviePO(driver);
        MoviePO moviePO = new MoviePO(driver);
        IndexPO indexPO = new IndexPO(driver);

        try {
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            driver.get("http://localhost:" + port);
            indexPO.clickLogInButton();
            Thread.sleep(500);
            signUpObject.enterCredentials("bar", "123");
            signUpObject.clickSubmit();
            Thread.sleep(500);
            assertThat(driver.getPageSource(), containsString("Welcome bar"));
            indexPO.clickMovieDetails("MBtnId_1");
            Thread.sleep(500);
            moviePO.writeInReviewArea("Test review");
            moviePO.clickPublish();

        } finally {
            driver.close();
        }
    }




}
