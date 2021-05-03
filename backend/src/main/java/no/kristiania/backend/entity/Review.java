package no.kristiania.backend.entity;

import org.hibernate.validator.constraints.Range;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Review implements Serializable {

    @EmbeddedId
    private ReviewId reviewId;

    @Range(min = 1, max = 5)
    private int rating;

    // TODO: column definition?
    @Column(columnDefinition = "text")
    private String reviewText;

    @NotNull
    private Date dateCreated;

    // TODO: use this?
/*    @PrePersist
    protected void onCreate() {
        dateCreated = new Date();
    }*/

    public ReviewId getReviewId() {
        return reviewId;
    }

    // TODO: this is red - why?
/*    public void setReviewId(ReviewId reviewId) {
        this.reviewId = reviewId;
    }*/

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    // TODO: get better date format?
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }
}
