package no.kristiania.backend.service;
import no.kristiania.backend.StubApplication;
import no.kristiania.backend.entity.Review;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test classes in many ways inspired by the test classes in same folder as ResetService etc

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class ReviewServiceTest extends ServiceTestBase {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateReview() {

        String title = "Title1";
        long movie1 = movieService.createMovie(title, "Director1", "Summary1");

        userService.createUser("foo", "123");

        reviewService.createReview(movie1, "foo", 5, "text");

        assertEquals(1, reviewService.getAllReviews(movie1).size());
    }

    @Test
    public void testGetSortedReviews() throws InterruptedException {

        long movie1 = movieService.createMovie("Title1","Director1", "Summary1");

        userService.createUser("foo", "123");
        userService.createUser("bar", "123");

        reviewService.createReview(movie1, "foo", 5, "text");
        Thread.sleep(2000); // sleep so theres a bit of difference in time on reviews
        reviewService.createReview(movie1, "bar", 3, "text");

        List<Review> byRating = reviewService.getAllReviewsSortedByAvgRating(movie1);
        assertTrue(byRating.get(0).getRating() > byRating.get(1).getRating());

        List<Review> byTime = reviewService.getAllReviewsSortedByTime(movie1);
        assertTrue(byTime.get(0).getDate().compareTo(byTime.get(1).getDate()) > 0);
    }



}
