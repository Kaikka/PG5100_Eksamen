package no.kristiania.frontend.controller;

import no.kristiania.backend.service.MovieService;
import no.kristiania.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;


@Named
@RequestScoped
public class CreateMovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    private String title;
    private String director;
    private String summary;

    public String CreateMovie(){

        if (title.isEmpty() || !(title.trim().length() > 0) || director.isEmpty() || !(director.trim().length() > 0) || summary.isEmpty() || !(summary.trim().length() > 0)) {
            return "/createmovie.jsf?faces-redirect=true&error=true";
        }

        movieService.createMovie(title, director, summary);

        return "/index.jsf?faces-redirect=true";

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}
