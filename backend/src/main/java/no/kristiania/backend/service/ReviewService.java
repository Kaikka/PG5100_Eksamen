package no.kristiania.backend.service;

import no.kristiania.backend.entity.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ReviewService {

    @Autowired
    private EntityManager em;


    public List<Review> getAllReviewsSortedByAvgRating(long movieId) {
        System.out.println("Getting all reviews sorted by rating");

        TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r WHERE r.reviewId.movieId=:movieId ORDER BY r.rating DESC", Review.class);
        query.setParameter("movieId", movieId);
        System.out.println(query.getResultList());
        return query.getResultList();
    }

    public List<Review> getAllReviewsSortedByTime(long movieId) {
        System.out.println("Getting all reviews sorted by time");

        TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r WHERE r.reviewId.movieId=:movieId ORDER BY r.dateCreated DESC", Review.class);
        query.setParameter("movieId", movieId);
        System.out.println(query.getResultList());
        return query.getResultList();
    }

    //TODO: delete review?
}
