package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.entity.ReviewId;
import no.kristiania.backend.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@Transactional
public class MovieService {

    @Autowired
    private EntityManager em;

    public Long createMovie(String title, int year, String director, String summary) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setYear(year);
        movie.setDirector(director);
        movie.setSummary(summary);

        System.out.println(
                "New movie added to db: " +
                title + " (" + year + "), " + "directed by " + director
        );

        em.persist(movie);

        return movie.getId();
    }

    // adapted from https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/c80e35b4181111f0e56ae7243bd5762756af4f90/intro/spring/jsf/src/main/java/org/tsdes/intro/spring/jsf/ex03/CommentService.java#L38
    public void deleteMovie(long id) {
        Movie movie = em.find(Movie.class, id);
        if (movie == null) {
            return;
        }
        em.remove(movie);
    }

    public List<Movie> getAllMovies(){
        TypedQuery<Movie> query = em.createQuery("select m from Movie m", Movie.class);
        return query.getResultList();
    }

    public Movie getMovie(long id){

        return em.find(Movie.class, id);
    }

    // adapted from https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/spring/security/manual/src/main/java/org/tsdes/intro/spring/security/manual/service/PostService.java
/*    public void deleteMovie(long id) {
        Query query = em.createQuery("DELETE FROM Movie m WHERE m.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }*/

    //TODO: test which delete above to use


    //TODO: refactor this, is very yoinked, importante
    public Double computeAverageRating(long movieId){
        TypedQuery<Double> queryAvg = em.createQuery(
                "select avg(r.rating) from Review r where r.reviewId.movieId=?1", Double.class);
        queryAvg.setParameter(1, movieId);

        //round average ratings down to one decimal e.g  3.7
        Double result =  queryAvg.getSingleResult();
        BigDecimal round = new BigDecimal(result).setScale(1, RoundingMode.HALF_UP);
        result = round.doubleValue();

        return result;
    }



    public ReviewId createReview(long movieId, String username, int rating, String reviewText) {

        Movie movie = em.find(Movie.class, movieId);
        if (movie == null) {
            throw new IllegalArgumentException("Movie with id "+movieId+" does not exist");
        }

        User user = em.find(User.class, username);
        if (user == null) {
            throw new IllegalArgumentException("User with username "+username+" does not exist");
        }

        ReviewId reviewId = new ReviewId(username, movieId);
        Review review = em.find(Review.class, reviewId);

        // If user has no review, create it, else update existing
        //TODO: check if this is correct comment :^)
        boolean persisted = review != null;
        if (!persisted) {
            review = new Review();
        }

        review.setRating(rating);
        review.setText(reviewText);
        review.setReviewId(reviewId);

        //TODO: something with rating on movies?
        //TODO yoinked, fix
        Double averageRating = computeAverageRating(movieId);
        movie.setAvgRating(averageRating);

        // TODO do I want to have this return something or just persist?
        return reviewId;
    }

    public List<Movie> getAllMoviesSortedByRating() {
        //TODO: sort by asc also?
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m ORDER BY m.avgRating DESC", Movie.class);

        System.out.println(query.getResultList());
        for (Movie m : query.getResultList()) {
            System.out.println(m.getTitle());
        }

        return query.getResultList();
    }

    public List<Movie> getAllMoviesSortedByYears() {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m ORDER BY m.year DESC", Movie.class);
        return query.getResultList();
    }

}
