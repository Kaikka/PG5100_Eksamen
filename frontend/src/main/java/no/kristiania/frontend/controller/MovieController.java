package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Review;
import no.kristiania.backend.service.MovieService;
import no.kristiania.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MovieController implements Serializable {


    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    private boolean hasReviews = false;

    private String rating;
    private List<Review> Reviews;
    private String reviewText = "";

    private int year;
    private String summary;

/*
    private Movie selectedMovie;


    */

    public boolean movieHasReviews() {
        return hasReviews;
    }

}
