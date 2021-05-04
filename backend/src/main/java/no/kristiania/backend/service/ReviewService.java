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
public class ReviewService {

    @Autowired
    private EntityManager em;

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

        // Create new review if it doesn't exist. Change this if decide to allow multiple comments per user
        boolean persisted = review != null;
        if (!persisted) {
            review = new Review();
        }

        System.out.println("Adding review for " + movie.getTitle() + ", " + rating + " stars, by " + username);

        review.setRating(rating);
        review.setReviewText(reviewText);
        review.setReviewId(reviewId);

        if(!persisted) {
            em.persist(review);
        }

        movie.setAvgRating(computeAverageRating(movieId));

        return reviewId;
    }

    public List<Review> getAllReviews(long movieId) {
        TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r WHERE r.reviewId.movieId=:movieId", Review.class);
        query.setParameter("movieId", movieId);
        return query.getResultList();
    }

    public List<Review> getAllReviewsSortedByAvgRating(long movieId) {

        TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r WHERE r.reviewId.movieId=:movieId ORDER BY r.rating DESC", Review.class);
        query.setParameter("movieId", movieId);
        return query.getResultList();
    }

    public List<Review> getAllReviewsSortedByTime(long movieId) {

        TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r WHERE r.reviewId.movieId=:movieId ORDER BY r.date DESC", Review.class);
        query.setParameter("movieId", movieId);
        return query.getResultList();
    }

    public Double computeAverageRating(long movieId){
        TypedQuery<Double> query = em.createQuery("SELECT AVG(r.rating) from Review r WHERE r.reviewId.movieId = :movieId", Double.class);
        query.setParameter("movieId", movieId);
        return round(query.getSingleResult());
    }

    /* adapted from https://www.baeldung.com/java-round-decimal-number#rounding-doubles-with-bigdecimal , unsure if this comment is needed as this seems like a very generic method */
    private static double round(double value) {
        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(1, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    //TODO: delete review?
}
