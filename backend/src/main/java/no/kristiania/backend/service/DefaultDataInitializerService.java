package no.kristiania.backend.service;

import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import java.util.function.Supplier;

/*
Class adapted from https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/exercise-solutions/quiz-game/part-11/backend/src/main/java/org/tsdes/intro/exercises/quizgame/backend/service/DefaultDataInitializerService.java
*/

public class DefaultDataInitializerService {


    @Autowired
    private UserService userService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private EntityManager em;


    @PostConstruct
    public void initialize() {

        attempt(() -> userService.createUser("foo", "123"));

        attempt(() -> userService.createUser("bar", "123"));

        Long CA = attempt(() -> movieService.createMovie(
                "Cloud Atlas",
                2012,
                "Lana Wachowski",
                "An exploration of how the actions of individual lives impact one another in the past, present and future, as one soul is shaped from a killer into a hero, and an act of kindness ripples across centuries to inspire a revolution."
        ));

        Long LOTR = attempt(() -> movieService.createMovie(
                "The Lord of the Rings: The Fellowship of the Ring",
                2001,
                "Peter Jackson",
                "A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron."
        ));

        Long Her = attempt(() -> movieService.createMovie(
                "Her",
                2013,
                "Spike Jonze",
                "In a near future, a lonely writer develops an unlikely relationship with an operating system designed to meet his every need."
        ));

        Long ExMachina = attempt(() -> movieService.createMovie(
                "Ex Machina",
                2014,
                "Alex Garland",
                "A young programmer is selected to participate in a ground-breaking experiment in synthetic intelligence by evaluating the human qualities of a highly advanced humanoid A.I."
        ));

        Long Hackers = attempt(() -> movieService.createMovie(
                "Hackers",
                1995,
                "Iain Softley",
                "Hackers are blamed for making a virus that will capsize five oil tankers."
        ));


        //TODO do I want to randomize this with ratings/users?

        attempt(() -> movieService.createReview(
                CA,
                "foo",
                5,
                "Possibly one of the best movies of all time."
        ));

        attempt(() -> movieService.createReview(
                ExMachina,
                "foo",
                4,
                "I think therefore am I?"
        ));

        attempt(() -> movieService.createReview(
                Hackers,
                "bar",
                4,
                "Although not very accurate, it is an excellent film. On the other hand, I am a sucker for Angelina Jolie so that might be the reason why I loved it. I recommend catching this one when it is on or renting it. Well worth it."
        ));

        attempt(() -> movieService.createReview(
                LOTR,
                "bar",
                5,
                "To Mordor!"
        ));

        attempt(() -> movieService.createReview(
                ExMachina,
                "foo",
                1,
                "Clumsy and Overblown Fodder for Aspiring Futurists."
        ));
    }

// This is copied from link supplied at top of class
    private  <T > T attempt(Supplier < T > lambda) {
        try {
            return lambda.get();
        } catch (Exception e) {
            return null;
        }
    }
}
