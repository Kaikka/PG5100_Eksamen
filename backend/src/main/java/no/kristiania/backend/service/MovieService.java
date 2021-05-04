package no.kristiania.backend.service;

import no.kristiania.backend.entity.Movie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
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
