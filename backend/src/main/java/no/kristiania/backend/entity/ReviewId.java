package no.kristiania.backend.entity;



import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/*
Class usage adapted from https://github.com/arcuri82/testing_security_development_enterprise_systems/blob/master/intro/jee/jpa/embedded/src/main/java/org/tsdes/intro/jee/jpa/embedded/UserId.java
*/

@Embeddable
public class ReviewId implements Serializable {

    // TODO: rename columns?
    @Column(name = "user_id")
    private String userId;

    @Column(name = "movie_id")
    private Long movieId;

    public ReviewId(){}

    public ReviewId(String userId, Long movieId) {
        this.userId = userId;
        this.movieId = movieId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getMovieId() {
        return movieId;
    }

    public void setMovieId(Long movieId) {
        this.movieId = movieId;
    }
}
