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

    public Long createMovie(String title, String director, String summary) {
        Movie movie = new Movie();
        movie.setTitle(title);
        movie.setDirector(director);
        movie.setSummary(summary);

        System.out.println("New movie added to db: " + title + ", " + "directed by " + director);

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

    public Movie getMovie(long id) {

        return em.find(Movie.class, id);
    }

    public List<Movie> getAllMovies() {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m", Movie.class);

        return query.getResultList();
    }

    public List<Movie> getAllMoviesSortedByDescRating() {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m ORDER BY m.avgRating DESC", Movie.class);

        return query.getResultList();
    }

    public List<Movie> getAllMoviesSortedByAscRating() {
        TypedQuery<Movie> query = em.createQuery("SELECT m FROM Movie m ORDER BY m.avgRating ASC", Movie.class);

        return query.getResultList();
    }
}
