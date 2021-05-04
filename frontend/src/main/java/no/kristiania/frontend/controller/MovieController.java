package no.kristiania.frontend.controller;

import no.kristiania.backend.entity.Movie;
import no.kristiania.backend.entity.Review;
import no.kristiania.backend.service.MovieService;
import no.kristiania.backend.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named
@SessionScoped
public class MovieController implements Serializable {


    @Autowired
    private MovieService movieService;

    @Autowired
    ReviewService reviewService;

    private boolean hasReviews = false;

    private Movie selectedMovie;

    private String rating;
    private List<Review> Reviews = new ArrayList<>();
    private String reviewText = "";

/*    private int year;*/
/*    private String summary;*/

/*
    private Movie selectedMovie;

TODO: all this yoinked

    */

    public List<Movie> getAllMoviesSortedByRating(){
        return movieService.getAllMoviesSortedByRating();
    }

    public String selectMovie(Movie movie){

        selectedMovie = movie;
        if(selectedMovie!= null){
            setMovieReviewsSortedByRating();
            hasReviews = !Reviews.isEmpty();
        }


        return "/moviedetails.jsf?faces-redirect=true";

    }


    public Movie getSelectedMovie() {
        return selectedMovie;
    }

    public void setMovieReviewsSortedByRating(){

        if(selectedMovie == null){
            return;
        }
        Reviews = reviewService.getAllReviewsSortedByAvgRating(selectedMovie.getId());
    }

    public void setMovieReviewsSortedByDate(){
        if(selectedMovie == null){
            return;
        }
        Reviews = reviewService.getAllReviewsSortedByTime(selectedMovie.getId());
    }

    public String addReview(){
        if(rating == null || reviewText.length()> 999){
            return "/moviedetails.jsf?faces-redirect=true&error=true";
        }else {
            int ratingValue = Integer.parseInt(rating);

            System.out.println("adding review..");

            movieService.createReview(selectedMovie.getId(), getUserName(), (ratingValue), reviewText);
            setMovieReviewsSortedByRating();

            setReviewText("");
            return "/moviedetails.jsf?faces-redirect=true";

        }

    }

    public String getUserName() {
        return ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
    }


    public boolean isHasReviews() {
        return hasReviews;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public List<Review> getMovieReviews() {
        return Reviews;
    }

}
