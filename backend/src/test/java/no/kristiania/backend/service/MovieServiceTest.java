package no.kristiania.backend.service;

import no.kristiania.backend.StubApplication;
import no.kristiania.backend.entity.Movie;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Test classes in many ways inspired by the test classes in same folder as ResetService etc

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = StubApplication.class, webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class MovieServiceTest extends ServiceTestBase {

    @Autowired
    private MovieService movieService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private UserService userService;

    @Test
    public void testNoMovie() {

        List<Movie> list = movieService.getAllMovies();
        assertEquals(0, list.size());
    }

    @Test
    public void testCreateMovie() {

        String title = "The Lord of the Foo Bar";
        long movieId = movieService.createMovie(title,"F. O. O. Barien", "Foo Bar goes to Mordor");

        assertNotNull(movieId);
    }

    @Test
    public void testGetMovie() {

        String title = "The Lord of the Foo Bar";
        long movieId = movieService.createMovie(title,"F. O. O. Barien", "Foo Bar goes to Mordor");

        assertEquals(title, movieService.getMovie(movieId).getTitle());
    }

    @Test
    public void testGetAllMovies() {

        long movie1 = movieService.createMovie("Title1","Director1", "Summary1");
        long movie2 = movieService.createMovie("Title2","Director2", "Summary2");
        long movie3 = movieService.createMovie("Title3","Director3", "Summary3");

        assertEquals(3, movieService.getAllMovies().size());
    }

    @Test
    public void testGetSortedMovies() {

        long movie1 = movieService.createMovie("Title1","Director1", "Summary1");
        long movie2 = movieService.createMovie("Title2","Director2", "Summary2");

        userService.createUser("foo", "123");

        reviewService.createReview(movie1, "foo", 5, "text");
        reviewService.createReview(movie2, "foo", 2, "text");

        List<Movie> desc = movieService.getAllMoviesSortedByDescRating();
        List<Movie> asc = movieService.getAllMoviesSortedByAscRating();
        assertEquals("Title1", desc.get(0).getTitle());
        assertEquals("Title2", asc.get(0).getTitle());
    }

    @Test
    public void testCreateReviewForMovieThatDoesNotExist() {
        userService.createUser("foo", "123");
        assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(1, "foo", 5, "text"));
    }

    @Test
    public void testCreateReviewWithUserThatDoesNotExist() {
        long movie1 = movieService.createMovie("Title1","Director1", "Summary1");
        assertThrows(IllegalArgumentException.class, () -> reviewService.createReview(movie1, "foo", 5, "text"));
    }
}